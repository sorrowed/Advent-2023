package advent.day9

import java.io.File

class Day9 {
    fun derive(primitives: List<Int>): List<List<Int>> {
        val result = mutableListOf(primitives)

        var current = primitives

        while (current.any { it != 0 }) {
            val derived = current
                    .foldIndexed(mutableListOf<Int>()) { index, acc, _ ->
                        if (index > 0) {
                            acc.add(current[index] - current[index - 1])
                        }
                        acc
                    }

            result.add(derived)

            current = derived
        }

        return result.toList()
    }

    fun extrapolateForwards(primitives: List<List<Int>>): List<List<Int>> {
        val result = mutableListOf<List<Int>>()

        for (index in (0..primitives.size - 1).reversed()) {
            val current = primitives[index].toMutableList()

            val new = if (index == primitives.size - 1) {
                current.last()
            } else {
                current.last() + result.last().last()
            }
            current.add(new)
            result.add(current)
        }
        return result.reversed().toList()
    }

    fun extrapolateBackwards(primitives: List<List<Int>>): List<List<Int>> {
        val result = mutableListOf<List<Int>>()

        for (index in (0..primitives.size - 1).reversed()) {
            val current = primitives[index].toMutableList()

            val new = if (index == primitives.size - 1) {
                current.first()
            } else {
                current.first() - result.last().first()
            }
            current.addFirst(new)
            result.add(current)
        }
        return result.reversed().toList()
    }

    fun part1() {

        val sumOfInterPolated = File("app/src/main/kotlin/advent/input/day9.txt")
                .readLines()
                .map { line -> line.split(' ').map { it.toInt() } }
                .sumOf { extrapolateForwards(derive(it))[0].last() }

        println("Day 9 Part 1 : $sumOfInterPolated")
    }

    fun part2() {
        val sumOfInterPolated = File("app/src/main/kotlin/advent/input/day9.txt")
                .readLines()
                .map { line -> line.split(' ').map { it.toInt() } }
                .sumOf { extrapolateBackwards(derive(it))[0].first() }
        println("Day 9 Part 2 : $sumOfInterPolated")
    }
}

fun run() {
    Day9().part1()
    Day9().part2()
}
