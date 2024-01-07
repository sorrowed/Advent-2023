package advent.day13

import advent.support.countDifferences
import java.io.File
import kotlin.math.min

class Pattern(val input: List<String>) {

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

    private fun smudgedColumns(): List<Int> {
        return (1..<input[0].length)
                .map { column -> column to columnSmudged(input, column) }
                .filter { it.second }
                .map { it.first }
    }

    private fun smudgedRows(): List<Int> {
        return (1..<input.size)
                .map { row -> row to rowSmudged(input, row) }
                .filter { it.second }
                .map { it.first }
    }

    private fun summarize(rows: () -> List<Int>, columns: () -> List<Int>): Int {
        return columns().sum() + 100 * rows().sum()
    }

    fun summarizeReflected(): Int {
        return summarize(::reflectedRows, ::reflectedColumns)
    }

    fun summarizeSmudged(): Int {
        return summarize(::smudgedRows, ::smudgedColumns)
    }

    companion object {

        private fun columnReflected(pattern: List<String>, column: Int): Boolean {

            val (left, right) = matchingColumns(pattern, column)

            return left == right
        }

        private fun rowReflected(pattern: List<String>, row: Int): Boolean {

            val (above, below) = matchingRows(pattern, row)

            return above == below
        }

        private fun columnSmudged(pattern: List<String>, column: Int): Boolean {

            val (left, right) = matchingColumns(pattern, column)

            return isSmudged(left, right)
        }

        fun rowSmudged(pattern: List<String>, row: Int): Boolean {

            val (above, below) = matchingRows(pattern, row)

            return isSmudged(above, below)
        }

        private fun matchingColumns(pattern: List<String>, column: Int): Pair<List<String>, List<String>> {

            val size = min(column, pattern[0].length - column)

            val left = pattern.map { s -> s.substring(column - size, column) }
            val right = pattern.map { s -> s.substring(column, column + size).reversed() }
            return left to right
        }

        private fun matchingRows(pattern: List<String>, row: Int): Pair<List<String>, List<String>> {

            val size = min(row, pattern.size - row)

            val above = pattern
                    .slice(row - size..<row)

            val below = pattern
                    .slice(row..<row + size)

            return above to below.reversed()
        }

        /**
         * See if there is exactely one char difference in the strings in these 'reflected' lists (which could be either rows or columns)
         */
        private fun isSmudged(first: List<String>, second: List<String>): Boolean {

            val zipped = first.zip(second)

            return first.isNotEmpty() && second.isNotEmpty() &&
                    zipped.count { columns -> columns.countDifferences() == 1 } == 1 &&
                    zipped.count { columns -> columns.countDifferences() == 0 } == zipped.count() - 1

        }
    }
}

class Day13 {
    private val original = File("app/src/main/kotlin/advent/input/day13.txt")
            .readLines()
            .joinToString("\n")
            .split("\n\n")
            .map { it.split("\n") }

    fun part1() {

        println("Day 13 part 1 : ${original.sumOf { Pattern(it).summarizeReflected() }}")
    }

    fun part2() {
        println("Day 13 part 2 : ${original.sumOf { Pattern(it).summarizeSmudged() }}")
    }
}

fun run() {
    Day13().part1()
    Day13().part2()
}