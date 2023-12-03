package advent.day3

import java.io.File
import kotlin.math.absoluteValue

data class Coordinate(val x: Int, val y: Int)

fun Coordinate.distance(that: Coordinate): Int {
    return (that.x - x).absoluteValue + (that.y - y).absoluteValue
}

data class Number(val value: Int, val topLeft: Coordinate, val bottomRight: Coordinate)

data class Symbol(val value: Char, val location: Coordinate)

fun Symbol.touches(number: Number): Boolean {
    return this.location.x >= number.topLeft.x - 1 &&
            this.location.x <= number.bottomRight.x + 1 &&
            this.location.y >= number.topLeft.y - 1 &&
            this.location.y <= number.bottomRight.y + 1
}

class Map {
    val numbers = mutableListOf<Number>()
    val symbols = mutableListOf<Symbol>()

    fun add(number: Number) {
        numbers.add(number)
    }

    fun add(symbol: Symbol) {
        symbols.add(symbol)
    }

    fun isPartNumber(number: Number): Boolean {
        return symbols.any() { symbol -> symbol.touches(number) }
    }

    companion object {
        fun parse(lines: List<String>): Map {
            val map = Map()

            for ((y, line) in lines.withIndex()) {
                var x = 0
                while (x < line.length) {

                    if (line[x].isDigit()) {
                        val start = x
                        var value = 0
                        while (x < line.length && line[x].isDigit()) {
                            value *= 10
                            value += line[x].digitToInt()
                            ++x
                        }
                        map.add(Number(value, Coordinate(start, y), Coordinate(x - 1, y)))
                    } else if (line[x] != '.') {
                        map.add(Symbol(line[x], Coordinate(x, y)))
                        ++x
                    } else {
                        ++x
                    }
                }
            }
            return map
        }
    }
}

class Day3 {
    fun part1() {
        val map = Map.parse(File("app/src/main/kotlin/advent/input/day3.txt").readLines())

        val sumOfPartNumbers = map.numbers.filter { number -> map.isPartNumber(number) }
                .fold(0) { sum, number -> sum + number.value }

        println("Day 3 Part 1 : $sumOfPartNumbers")
    }

    fun part2() {}
}

fun run() {
    Day3().part1()
    Day3().part2()
}
