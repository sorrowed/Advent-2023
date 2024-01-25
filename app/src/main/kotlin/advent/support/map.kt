package advent.support

import java.time.temporal.TemporalAmount
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt


data class Position(val x: Long, val y: Long) {
    fun manhattan(to: Position): Long {
        return abs(to.x - x) + abs(to.y - y)
    }

    fun distance(to: Position): Double {

        return sqrt((to.x - this.x).toDouble().pow(2) + (to.y - this.y).toDouble().pow(2))
    }

    fun within(topLeft: Position, bottomRight: Position): Boolean {
        return x >= topLeft.x && x <= bottomRight.x &&
                y >= topLeft.y && y <= bottomRight.y
    }

    fun move(direction: Direction, amount: Long = 1): Position {
        return when (direction) {
            Direction.UP -> Position(x, y - amount)
            Direction.LEFT -> Position(x - amount, y)
            Direction.DOWN -> Position(x, y + amount)
            Direction.RIGHT -> Position(x + amount, y)
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