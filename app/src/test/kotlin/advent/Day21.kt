package advent

import kotlin.test.Test
import kotlin.test.assertEquals
import advent.day21.*
import advent.support.Position

class Day21 {

    val input = listOf(
            "...........",
            ".....###.#.",
            ".###.##..#.",
            "..#.#...#..",
            "....#.#....",
            ".##..S####.",
            ".##..#...#.",
            ".......##..",
            ".##.#.####.",
            ".##..##.##.",
            "...........",
    )


    @Test
    fun `given input when parsed then result is correct`() {
        val farm = Farm from input

        val start = farm.plots.entries.find { it.value == Plot.START }!!.key
        assertEquals(Position(5L, 5L), start)

        val n = farm.neighbors(start)
        assertEquals(listOf(Position(4L, 5L), Position(5L, 4L)), n)

        var t = farm.neighbors(start).toSet()
        repeat(6 - 1) {
            t = t.flatMap { farm.neighbors(it) }.toSet()
        }
        assertEquals(16, t.size)

        farm.print(t)
    }
}
