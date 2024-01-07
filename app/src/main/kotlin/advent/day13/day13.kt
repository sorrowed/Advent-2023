package advent.day13

import kotlin.math.min
import java.io.File

class Pattern(val input: List<String>) {
    private fun columnReflected(pattern: List<String>, column: Int): Boolean {
        return pattern.all { s: String ->
            val size = min(column, s.length - column)

            s.substring(column - size, column) == s.substring(column, column + size).reversed()
        }
    }

    private fun rowReflected(pattern: List<String>, row: Int): Boolean {

        val size = min(row, pattern.size - row)

        val above = pattern
                .slice(row - size..<row)

        val below = pattern
                .slice(row..<row + size)

        return above == below.reversed()
    }

    fun reflectedColumns(): List<Int> {
        return (1..<input[0].length)
                .map { column -> column to columnReflected(input, column) }
                .filter { it.second }
                .map { it.first }

    }

    fun reflectedRows(): List<Int> {
        return (1..<input.size)
                .map { row -> row to rowReflected(input, row) }
                .filter { it.second }
                .map { it.first }
    }

    fun summarize(): Int {
        return reflectedColumns().sum() + 100 * reflectedRows().sum()
    }
}

class Day13 {
    private val original = File("app/src/main/kotlin/advent/input/day13.txt")
            .readLines()
            .joinToString("\n")
            .split("\n\n")
            .map { it.split("\n") }

    fun part1() {

        println("Day 13 part 1 : ${original.sumOf { Pattern(it).summarize() }}")
    }

    fun part2() {
        println("Day 13 part 2 : ")
    }
}

fun run() {
    Day13().part1()
    Day13().part2()
}