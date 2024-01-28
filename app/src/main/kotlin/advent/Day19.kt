package advent.day19

import java.io.File

data class Part(val x: Long, val m: Long, val a: Long, val s: Long) {

    val rating = x + m + a + s

    companion object {
        fun parse(input: String): Part {

            val re = Regex("""\{x=(\d*),m=(\d*),a=(\d*),s=(\d*)\}""").matchEntire(input)!!

            return Part(re.groups[1]!!.value.toLong(),
                    re.groups[2]!!.value.toLong(),
                    re.groups[3]!!.value.toLong(),
                    re.groups[4]!!.value.toLong())
        }
    }
}

data class MinMaxRating(val x: Pair<Long, Long>, val m: Pair<Long, Long>, val a: Pair<Long, Long>, val s: Pair<Long, Long>) {
    constructor() : this(
            Pair(1, 4000),
            Pair(1, 4000),
            Pair(1, 4000),
            Pair(1, 4000)
    )

    fun firstPart(operator: Char, at: Long) = if (operator == '<') at else at + 1
    fun secondPart(operator: Char, at: Long) = if (operator == '<') at - 1 else at

    fun split(component: Char, operator: Char, at: Long): Pair<MinMaxRating, MinMaxRating> {
        val lower = when (component) {
            'x' -> copy(x = x.first to secondPart(operator, at))
            'm' -> copy(m = m.first to secondPart(operator, at))
            'a' -> copy(a = a.first to secondPart(operator, at))
            's' -> copy(s = s.first to secondPart(operator, at))
            else -> error("Invalid component in rule")
        }

        val higher = when (component) {
            'x' -> copy(x = firstPart(operator, at) to x.second)
            'm' -> copy(m = firstPart(operator, at) to m.second)
            'a' -> copy(a = firstPart(operator, at) to a.second)
            's' -> copy(s = firstPart(operator, at) to s.second)
            else -> error("Invalid component in rule")
        }

        return lower to higher
    }

    val combinations = (x.second - x.first + 1) *
            (m.second - m.first + 1) *
            (a.second - a.first + 1) *
            (s.second - s.first + 1)
}

fun minmax(ratings: MutableList<MinMaxRating>, workflows: Map<String, Workflow>, workflow: Workflow, minmax: MinMaxRating) {

    var current = minmax

    for (rule in workflow.rules) {

        val (lower, higher) = current.split(rule.component, rule.operator, rule.operand)

        // Split the range based on the operator then see what to do with it:
        if (rule.operator == '<') {
            // - If the destination was A -> add lower to accepted ratings and continue with higher
            // - If the destination was R -> discard lower and continue with higher
            // - If the destination was another workflow, get that workflow and process lower range with the new workflow, continue this with higher
            when (rule.destination) {
                "A" -> ratings.add(lower)
                "R" -> {}
                else -> minmax(ratings, workflows, workflows[rule.destination]!!, lower)
            }
            current = higher

        } else if (rule.operator == '>') {
            // - If the destination was A -> add higher to accepted ratings and continue with lower
            // - If the destination was R -> discard higer and continue with lower
            // - If the destination was another workflow, get that workflow and process higher range with the new workflow, continue this with lower
            when (rule.destination) {
                "A" -> ratings.add(higher)
                "R" -> {}
                else -> minmax(ratings, workflows, workflows[rule.destination]!!, higher)
            }
            current = lower
        } else {
            error("Unknown operator")
        }
    }

    // None of rules matched, if the fallback was "A" then this is also a match. If it is not "R" follow the fallback workflow
    if (workflow.fallback == "A") {
        ratings.add(current)
    } else if (workflow.fallback != "R") {
        minmax(ratings, workflows, workflows[workflow.fallback]!!, current)
    }
}

class Rule(val input: String, val destination: String) {
    val operator = input[1]
    val operand = input.substring(2).toLong()
    val component = input[0]

    fun compare(part: Part): Boolean {
        val value = when (component) {
            'x' -> part.x
            'm' -> part.m
            'a' -> part.a
            's' -> part.s
            else -> error("Invalid component int rule")
        }

        return when (operator) {
            '<' -> value < operand
            '>' -> value > operand
            else -> error("Invalid operator int rule")
        }
    }

    companion object {
        fun parse(input: String): Rule {

            val tokens = input.split(':')

            return Rule(tokens[0], tokens[1])
        }
    }
}

class Workflow(val name: String, val rules: List<Rule>, val fallback: String) {

    fun process(part: Part): String {
        for (rule in rules) {
            if (rule.compare(part)) {
                return rule.destination
            }
        }
        return fallback
    }

    companion object {
        fun parse(input: String): Workflow {

            input.split('{').also { tokens ->

                val rules = tokens[1]
                        .removeSuffix("}")
                        .split(",")

                val fallback = rules.last()

                return Workflow(tokens[0], rules.dropLast(1).map { rule -> Rule.parse(rule) }, fallback)
            }
        }
    }
}

fun process(workflows: Map<String, Workflow>, parts: List<Part>): Pair<List<Part>, List<Part>> {
    val accepted = mutableListOf<Part>()
    val rejected = mutableListOf<Part>()

    for (part in parts) {
        var destination = workflows["in"]!!.process(part)

        while (true) {
            when (destination) {
                "A" -> {
                    accepted += part
                    break
                }

                "R" -> {
                    rejected += part
                    break
                }

                else -> {
                    destination = workflows[destination]!!.process(part)

                }
            }
        }
    }
    return accepted to rejected
}

class Day19 {
    private val rulesInput = File("app/src/main/kotlin/advent/input/day19-rules.txt").readLines()
    private val partsInput = File("app/src/main/kotlin/advent/input/day19-parts.txt").readLines()

    fun part1() {
        val workflows = rulesInput.map { Workflow.parse(it) }.associateBy { it.name }
        val parts = partsInput.map { Part.parse(it) }

        val (accepted, _) = process(workflows, parts)

        println("Day 19 part 1 : ${accepted.sumOf { it.rating }}")
    }


    fun part2() {
        val workflows = rulesInput.map { Workflow.parse(it) }.associateBy { it.name }
        val ratings = mutableListOf<MinMaxRating>()

        val workflow = workflows["in"]!!
        minmax(ratings, workflows, workflow, MinMaxRating())

        println("Day 19 part 2 : ${ratings.sumOf { it.combinations }}")
    }
}

fun run() {
    Day19().part1()
    Day19().part2()
}