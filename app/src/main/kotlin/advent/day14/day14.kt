package advent.day14

import java.io.File
import java.lang.StringBuilder

data class Platform(val rocks: List<String>) {
    enum class RockType(val v: Char) {
        Empty('.'),
        Round('O')
    }

    fun tiltNorth(): Platform {
        return Platform(tiltNorthSouth(rocks))
    }

    fun cycle(): Platform {
        return tiltNorth().tiltWest().tiltSouth().tiltEast()
    }

    fun cycle(cycles: Int): Platform {

        val cache = mutableMapOf<Platform, Int>()

        var platform = this

        var cycle = 0
        var cycled = false
        while (cycle < cycles) {

            if (!cycled && cache.putIfAbsent(platform, cycle) != null) {
                error("That ain't right")
            }

            platform = platform.cycle()
            ++cycle

            val previous = cache[platform]
            if (!cycled && previous != null && previous >= 0 && previous != cycle) {

                cycle = cycles - ((cycles - cycle) % (cycle - previous))

                cycled = true
            }
        }

        return platform
    }

    private fun tiltSouth(): Platform {
        return Platform(tiltNorthSouth(rocks.reversed()).reversed())
    }

    private fun tiltEast(): Platform {
        return Platform(tiltEastWest(rocks.map { it.reversed() }).map { it.reversed() })
    }

    private fun tiltWest(): Platform {
        return Platform(tiltEastWest(rocks))
    }

    private fun tiltNorthSouth(original: List<String>): List<String> {
        val tilted = original.toMutableList()

        for (column in 0..<tilted[0].length) {

            for (row in 0..<tilted.size - 1) {

                if (tilted[row][column] == RockType.Empty.v) {
                    for (nextRow in row + 1..<tilted.size) {

                        if (tilted[nextRow][column] == RockType.Empty.v) {
                            continue
                        } else if (tilted[nextRow][column] == RockType.Round.v) {

                            val swapped = swap(tilted[row] to tilted[nextRow], column)
                            tilted[row] = swapped.first
                            tilted[nextRow] = swapped.second
                        }

                        break
                    }
                }
            }
        }
        return tilted
    }

    private fun tiltEastWest(original: List<String>): List<String> {
        val tilted = original.toMutableList()
        for (row in 0..<tilted.size) {
            for (column in 0..<tilted[0].length - 1) {
                if (tilted[row][column] == RockType.Empty.v) {
                    for (nextColumn in column + 1..<tilted[0].length) {

                        if (tilted[row][nextColumn] == RockType.Empty.v) {
                            continue
                        } else if (tilted[row][nextColumn] == RockType.Round.v) {
                            tilted[row] = swap(tilted[row], column, nextColumn)
                        }
                        break
                    }
                }
            }
        }
        return tilted
    }

    fun totalLoad(): Int {
        return rocks.withIndex().sumOf { s ->
            s.value.sumOf {
                if (it == RockType.Round.v) {
                    rocks.size - s.index
                } else {
                    0
                }
            }
        }
    }

    private fun swap(orig: Pair<String, String>, index: Int): Pair<String, String> {

        val original = orig.first[index]
        val a = StringBuilder(orig.first)
        a.setCharAt(index, orig.second[index])

        val b = StringBuilder(orig.second)
        b.setCharAt(index, original)

        return a.toString() to b.toString()
    }

    private fun swap(orig: String, left: Int, right: Int): String {

        val original = orig[left]

        val a = StringBuilder(orig)

        a.setCharAt(left, orig[right])
        a.setCharAt(right, original)

        return a.toString()
    }
}

class Day14 {
    private val input = File("app/src/main/kotlin/advent/input/day14.txt")
            .readLines()

    fun part1() {
        println("Day 14 part 1 : ${Platform(input).tiltNorth().totalLoad()}")
    }

    fun part2() {
        // FIXME: Off by two, should be 83790. Why?
        println("Day 14 part 2 : ${Platform(input).cycle(1_000_000_000).totalLoad()}")
    }
}

fun run() {
    Day14().part1()
    Day14().part2()
}