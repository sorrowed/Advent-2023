package advent.day11

import advent.support.Position
import advent.support.pairs
import java.io.File

enum class Tile(val type: Char) {
    EMPTY('.'),
    GALAXY('#');

    companion object {
        private val map = entries.associateBy { it.type }
        infix fun from(value: Char) = map[value]
    }
}

class Universe(val tiles: Map<Position, Tile>) {

    fun galaxies(): List<Position> {
        return tiles.entries
                .filter { it.value == Tile.GALAXY }
                .map { it.key }
    }

    companion object {
        fun parse(input: List<String>): Universe {

            val tiles = input
                    .flatMapIndexed { y, line -> line.mapIndexed { x, char -> Position(x.toLong(), y.toLong()) to (Tile from char)!! } }
                    .associate { it.first to it.second }

            return Universe(tiles)
        }
    }
}

fun emptyRowsAndColumns(input: List<String>): Pair<List<Int>, List<Int>> {
    val rows = input.indices
            .filter { row -> input[row].all { it == '.' } }

    val columns = (0..<input[0].length)
            .filter { column ->
                input.all { line -> line[column] == '.' }
            }
    return rows to columns
}

fun crossedRowsAndColumns(pair: Pair<Position, Position>, emptyRows: List<Int>, emptyColums: List<Int>): Int {

    val (rs, re) = minOf(pair.first.y, pair.second.y) to maxOf(pair.first.y, pair.second.y)
    val crossedRows = emptyRows.count { rs < it && it < re }

    val (cs, ce) = minOf(pair.first.x, pair.second.x) to maxOf(pair.first.x, pair.second.x)
    val crossedColumns = emptyColums.count { cs < it && it < ce }

    return crossedColumns + crossedRows
}

fun expandInput(input: List<String>): List<String> {
    val (rows, columns) = emptyRowsAndColumns(input)

    val result = mutableListOf<String>()
    for (r in input.indices) {
        var s = ""
        for (c in (0..<input[r].length)) {
            s += input[r][c]
            if (columns.contains(c)) {
                s += '.'
            }
        }
        result += s
        if (rows.contains(r)) {
            result += s
        }
    }

    return result
}

class Day11 {
    private val original = File("app/src/main/kotlin/advent/input/day11.txt").readLines()

    fun part1() {

        val galaxyPairs = Universe
                .parse(expandInput(original))
                .galaxies()
                .pairs()

        println("Day 11 part 1 : ${galaxyPairs.sumOf { it.first.manhattan(it.second) }}")
    }

    fun part2() {
        // Count the number of empty rows, and empty columns between each pair and add these * (1000000-1) to the distance

        val (emptyRows, emptyColumns) = emptyRowsAndColumns(original)
        val galaxyPairs = Universe.parse(original).galaxies().pairs()

        val crossed = galaxyPairs.map { it to crossedRowsAndColumns(it, emptyRows, emptyColumns) }
                .associate { pair -> pair.first to pair.second }

        println("Day 11 part 2 : ${galaxyPairs.sumOf { it.first.manhattan(it.second) + (crossed[it]!! * (1_000_000L - 1)) }}")
    }
}

fun run() {
    Day11().part1()
    Day11().part2()
}

