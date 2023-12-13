package advent.day8

import advent.support.lcm
import java.io.File

data class DesertMap(val instructions: String, val nodes: Map<String, Pair<String, String>>) {

    fun traversePart1(): Int {
        var currentNode = nodes["AAA"]
        var steps = 0
        while (currentNode != nodes["ZZZ"]) {
            currentNode = if (instructions[steps % instructions.length] == 'L') {
                nodes[currentNode!!.first]
            } else {
                nodes[currentNode!!.second]
            }
            ++steps
        }

        return steps
    }

    fun traversePart2(): Long {
        val currentNodes = nodes.filter { it.key.endsWith('A') }.map { Pair(it.key, it.value) }.toMutableList()
        var steps = 0L
        while (currentNodes.any { !it.first.endsWith('Z') }) {
            for (i in currentNodes.indices) {
                currentNodes[i] = if (instructions[(steps % instructions.length).toInt()] == 'L') {
                    currentNodes[i].second.first to nodes[currentNodes[i].second.first]!!
                } else {
                    currentNodes[i].second.second to nodes[currentNodes[i].second.second]!!
                }
            }
            ++steps
        }
        return steps
    }

    fun traversePart3(): Long {
        val currentNodes = nodes.filter { it.key.endsWith('A') }.map { Pair(it.key, it.value) }.toMutableList()
        val allSteps = MutableList(currentNodes.size) { 0L }
        for (i in currentNodes.indices) {

            var steps = 0L
            while (!currentNodes[i].first.endsWith('Z')) {
                currentNodes[i] = if (instructions[(steps % instructions.length).toInt()] == 'L') {
                    currentNodes[i].second.first to nodes[currentNodes[i].second.first]!!
                } else {
                    currentNodes[i].second.second to nodes[currentNodes[i].second.second]!!
                }
                ++steps
            }
            allSteps[i] = steps
        }

        return lcm(allSteps)
    }

    companion object {
        fun parse(input: List<String>): DesertMap {
            val instructions = input[0]
            val nodes = input
                    .slice(2..<input.size)
                    .map { it.split(" = (", ", ", ")") }
                    .associateBy({ it[0] }, { Pair(it[1], it[2]) })
            return DesertMap(instructions, nodes)
        }
    }
}


class Day8 {
    val map = DesertMap.parse(File("app/src/main/kotlin/advent/input/day8.txt").readLines())

    fun part1() {
        println("Day 8 Part 1 : ${map.traversePart1()}")
    }

    fun part2() {
        println("Day 8 Part 2 : ${map.traversePart3()}")
    }
}

fun run() {
    Day8().part1()
    Day8().part2()
}