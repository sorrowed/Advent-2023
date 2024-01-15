package advent.day15

import java.io.File

data class Hash(val input: List<String>) {

    companion object {
        fun hash(start: Int, line: String): Int {
            return line.fold(start) { acc, c -> ((acc + c.code) * 17) % 256 }
        }
    }

    fun hash(): Int {
        return input.sumOf { current -> hash(0, current) }
    }
}

data class Step(val label: String, val operation: Char, val focalLength: Int? = null) {

    val boxNumber = Hash.hash(0, label)

    companion object {
        fun parse(line: String): Step {

            val tokens = line.split('-', '=')
            return if (line.contains("-")) {
                Step(tokens[0], '-')
            } else if (line.contains("=")) {
                Step(tokens[0], '=', tokens[1].toInt())
            } else {
                error("Invalid input in sequence")
            }
        }
    }
}

class BoxArray(steps: List<Step>) {
    val boxes: Map<Int, List<Step>>

    init {
        val result = (0..255).associateWith { mutableListOf<Step>() }

        for (step in steps) {
            val box = result[step.boxNumber]!!
            val existing = box.indices.find { box[it].label == step.label };

            if (step.operation == '=') {
                if (existing != null) {
                    box[existing] = step
                } else {
                    box += step
                }
            } else if (step.operation == '-') {
                if (existing != null) {
                    box.removeAt(existing)
                }
            } else {
                error("Help!")
            }
        }
        boxes = result
    }

    fun focalPower(): Int {
        return boxes.entries.sumOf { entry -> (1 + entry.key) * entry.value.withIndex().sumOf { (1 + it.index) * it.value.focalLength!! } }
    }
}

class Day15 {
    private val input = File("app/src/main/kotlin/advent/input/day15.txt")
            .readText().split(',')

    fun part1() {
        println("Day 15 part 1 : ${Hash(input).hash()}")
    }

    fun part2() {

        val array = BoxArray(input.map { Step.parse(it) })

        println("Day 15 part 2 : ${array.focalPower()}")
    }
}

fun run() {
    Day15().part1()
    Day15().part2()
}