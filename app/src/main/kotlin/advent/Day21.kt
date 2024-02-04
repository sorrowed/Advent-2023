package advent.day21

import advent.support.Position
import java.io.File

enum class Plot(val type: Char) {
    START('S'),
    GARDEN('.'),
    ROCK('#'),
    MARKED('O');

    companion object {
        private val map = entries.associateBy { it.type }
        infix fun from(value: Char) = map[value]
    }
}

data class Farm(val plots: Map<Position, Plot>) {

    private val limits = Position(plots.keys.minBy { it.x }.x, plots.keys.minBy { it.y }.y) to
            Position(plots.keys.maxBy { it.x }.x, plots.keys.maxBy { it.y }.y)

    fun neighbors(location: Position): List<Position> {

        val options = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
                .map { offset -> Position(location.x + offset.first, location.y + offset.second) }

        return options.filter { position -> position.within(limits.first, limits.second) && plots[position]!! != Plot.ROCK }
    }

    fun print(marked: Set<Position>) {

        for (y in limits.first.y..limits.second.y) {
            for (x in limits.first.x..limits.second.x) {

                val position = Position(x, y)

                print(if (marked.contains(position)) {
                    Plot.MARKED.type
                } else {
                    plots[position]!!.type
                })
            }
            println()
        }
    }

    companion object {
        infix fun from(input: List<String>): Farm {
            return Farm(input.flatMapIndexed { y: Int, s: String -> s.mapIndexed { x, c -> Position(x.toLong(), y.toLong()) to (Plot from c)!! } }
                    .associate { it.first to it.second })
        }
    }
}

class Day21 {

    private val farm = Farm from File("app/src/main/kotlin/advent/input/day21.txt").readLines()

    fun part1() {

        val start = farm.plots.entries.first { it.value == Plot.START }.key

        var marked = farm.neighbors(start).toSet()
        repeat(64 - 1) {
            marked = marked.flatMap { farm.neighbors(it) }.toSet()
        }

        //farm.print(marked)

        println("Day 21 part 1 : ${marked.size}")
    }

    fun part2() {
    }
}

fun run() {
    Day21().part1()
    Day21().part2()
}