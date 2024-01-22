package advent.support

import kotlin.math.abs


data class Position(val x: Int, val y: Int) {
    fun manhattan(to: Position): Int {
        return abs(to.x - x) + abs(to.y - y)
    }

    fun within(topLeft: Position, bottomRight: Position): Boolean {
        return x >= topLeft.x && x <= bottomRight.x &&
                y >= topLeft.y && y <= bottomRight.y
    }

    fun move(direction: Direction): Position {
        return when (direction) {
            Direction.UP -> Position(x, y - 1)
            Direction.LEFT -> Position(x - 1, y)
            Direction.DOWN -> Position(x, y + 1)
            Direction.RIGHT -> Position(x + 1, y)
            else -> error("Invalid direction")
        }
    }
}

enum class Direction(val source: Char) {
    UP('^'),
    LEFT('<'),
    DOWN('v'),
    RIGHT('>'),
    UNKNOWN('?');

    fun opposite(): Direction {
        return when (this) {
            UP -> DOWN
            LEFT -> RIGHT
            DOWN -> UP
            RIGHT -> LEFT
            else -> error("Invalid direction")
        }
    }

    fun left(): Direction {
        return when (this) {
            UP -> LEFT
            LEFT -> DOWN
            DOWN -> RIGHT
            RIGHT -> UP
            else -> error("Invalid direction")
        }
    }

    fun right(): Direction {
        return when (this) {
            UP -> RIGHT
            LEFT -> UP
            DOWN -> LEFT
            RIGHT -> DOWN
            else -> error("Invalid direction")
        }
    }

    companion object {
        private val map = entries.associateBy { it.source }
        infix fun from(value: Char) = map[value]
    }
}