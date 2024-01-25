package advent.day18

import advent.support.*
import java.io.File

fun String.parseDirection(): Direction {
    return when (this) {
        "R" -> Direction.RIGHT
        "D" -> Direction.DOWN
        "L" -> Direction.LEFT
        "U" -> Direction.UP
        else -> {
            error("Invalid direction token $this")
        }
    }
}

fun Char.parseDirection(): Direction {
    return when (this) {
        '0' -> Direction.RIGHT
        '1' -> Direction.DOWN
        '2' -> Direction.LEFT
        '3' -> Direction.UP
        else -> {
            error("Invalid direction token $this")
        }
    }
}

data class Diginstruction(val direction: Direction, val amount: Long) {
    companion object {
        fun parse(input: String): Diginstruction {
            val tokens = input.split(' ')

            return Diginstruction(tokens[0].parseDirection(), tokens[1].toLong())
        }

        fun parseRevised(input: String): Diginstruction {

            val token = input.split(' ')
                    .last()
                    .removeSurrounding("(#", ")")

            return Diginstruction(token.last().parseDirection(), token.substring(0, token.length - 1).toLong(16))
        }
    }
}

data class Digplan(val instructions: List<Diginstruction>) {
    val border = instructions.fold(mutableSetOf(Position(0, 0)))
    { set, instruction ->
        val p = set.last().move(instruction.direction, instruction.amount)
        set += p
        set
    }.toSet()

    fun area(): Long {
        val perimeter = perimeter(border).toLong()

        return pointsInside(area(border), perimeter) + perimeter
    }

    companion object {
        fun parse(input: List<String>): Digplan {
            return Digplan(input.map { Diginstruction.parse(it) })
        }

        fun parseRevised(input: List<String>): Digplan {
            return Digplan(input.map { Diginstruction.parseRevised(it) })
        }
    }
}

class Day18 {
    private val input = File("app/src/main/kotlin/advent/input/day18.txt").readLines()

    fun part1() {
        val plan = Digplan.parse(input)

        println("Day 18 part 1 : ${plan.area()}")
    }


    fun part2() {
        val plan = Digplan.parseRevised(input)

        println("Day 18 part 2 : ${plan.area()}")
    }
}

fun run() {
    Day18().part1()
    Day18().part2()
}