package advent.day16

import advent.support.Direction
import advent.support.Position
import java.io.File

enum class Location(val source: Char) {
    EMPTY('.'),
    VERTICAL('|'),
    HORIZONTAL('-'),
    BACKSLANT('\\'),
    FORWARDSLANT('/');


    companion object {
        private val map = entries.associateBy { it.source }
        infix fun from(value: Char) = map[value]
    }
}

class Beam(val position: Position, val direction: Direction) {

}

class Contraption(val locations: Map<Position, Location>) {

    val limits: Pair<Position, Position> =
            Position(locations.keys.minBy { it.x }.x, locations.keys.minBy { it.y }.y) to Position(locations.keys.maxBy { it.x }.x, locations.keys.maxBy { it.y }.y)

    private fun isOutsideLimits(position: Position): Boolean {
        return position.x < limits.first.x || position.x > limits.second.x ||
                position.y < limits.first.y || position.y > limits.second.y
    }

    private fun isEmptySpace(direction: Direction, location: Location): Boolean {
        return location == Location.EMPTY ||
                ((direction == Direction.DOWN || direction == Direction.UP) && location == Location.VERTICAL) ||
                ((direction == Direction.LEFT || direction == Direction.RIGHT) && location == Location.HORIZONTAL)
    }

    private fun move(position: Position, direction: Direction): Position {
        return when (direction) {
            Direction.UP -> Position(position.x, position.y - 1)
            Direction.DOWN -> Position(position.x, position.y + 1)
            Direction.LEFT -> Position(position.x - 1, position.y)
            Direction.RIGHT -> Position(position.x + 1, position.y)
            else -> error("Invalid direction")
        }
    }

    private fun rotate(location: Location, direction: Direction): Direction {
        return when (location) {
            Location.BACKSLANT -> {
                when (direction) {
                    Direction.UP -> Direction.LEFT
                    Direction.DOWN -> Direction.RIGHT
                    Direction.LEFT -> Direction.UP
                    Direction.RIGHT -> Direction.DOWN
                    else -> error("Invalid direction")
                }
            }

            Location.FORWARDSLANT -> {
                when (direction) {
                    Direction.UP -> Direction.RIGHT
                    Direction.DOWN -> Direction.LEFT
                    Direction.LEFT -> Direction.DOWN
                    Direction.RIGHT -> Direction.UP
                    else -> error("Invalid direction")
                }
            }

            else -> {
                error("Help!")
            }
        }
    }

    private fun beam(trail: MutableSet<Pair<Position, Direction>>, position: Position, direction: Direction) {

        if (trail.contains(position to direction)) return

        var currentDir = direction
        var currentPos = position

        while (!isOutsideLimits(currentPos)) {
            trail.add(currentPos to currentDir)

            val location = locations[currentPos]!!
            if (isEmptySpace(currentDir, location)) {
                currentPos = move(currentPos, currentDir)
            } else if (location == Location.BACKSLANT || location == Location.FORWARDSLANT) {
                currentDir = rotate(location, currentDir)
                currentPos = move(currentPos, currentDir)
            } else if (location == Location.HORIZONTAL) {
                beam(trail, move(currentPos, Direction.LEFT), Direction.LEFT)
                beam(trail, move(currentPos, Direction.RIGHT), Direction.RIGHT)
                break
            } else if (location == Location.VERTICAL) {
                beam(trail, move(currentPos, Direction.UP), Direction.UP)
                beam(trail, move(currentPos, Direction.DOWN), Direction.DOWN)
                break
            } else {
                error("Help!")
            }
        }
    }

    fun trail(position: Position, direction: Direction): Set<Pair<Position, Direction>> {

        val result = mutableSetOf<Pair<Position, Direction>>()
        beam(result, position, direction)
        return result
    }

    companion object {
        fun parse(input: List<String>): Contraption {
            return Contraption(input.flatMapIndexed { y: Int, s: String -> s.mapIndexed { x: Int, c: Char -> Position(x, y) to (Location from c)!! } }.associate { it })
        }

        fun print(contraption: Contraption, energized: Set<Pair<Position, Direction>>) {

            val limits = contraption.limits

            for (y in limits.first.y..limits.second.y) {
                for (x in limits.first.x..limits.second.x) {

                    if (energized.find { it.first == Position(x, y) } != null) {
                        print('#')
                    } else {
                        //print(contraption.locations[Position(x, y)]!!.source)
                        print('.')
                    }
                }
                println()
            }
        }
    }
}

class Day16 {
    private val contraption = File("app/src/main/kotlin/advent/input/day16.txt").readLines().run { Contraption.parse(this) }


    fun part1() {

        val beams = contraption.trail(Position(0, 0), Direction.RIGHT)

        println("Day 16 part 1 : ${beams.distinctBy { it.first }.count()}")
    }

    fun part2() {
        val limits = contraption.limits

        val upper = (limits.first.x..limits.second.x).maxOf { x -> contraption.trail(Position(x, limits.first.y), Direction.DOWN).distinctBy { it.first }.count() }
        val lower = (limits.first.x..limits.second.x).maxOf { x -> contraption.trail(Position(x, limits.second.y), Direction.UP).distinctBy { it.first }.count() }
        val left = (limits.first.y..limits.second.y).maxOf { y -> contraption.trail(Position(limits.first.x, y), Direction.RIGHT).distinctBy { it.first }.count() }
        val right = (limits.first.y..limits.second.y).maxOf { y -> contraption.trail(Position(limits.second.x, y), Direction.LEFT).distinctBy { it.first }.count() }

        println("Day 16 part 2 : ${maxOf(upper, lower, left, right)}")
    }
}

fun run() {
    Day16().part1()
    Day16().part2()
}