package advent

import advent.day6.Race

import kotlin.test.Test
import kotlin.test.assertEquals

class Day6 {

    private val input = listOf(7, 15, 30).zip(listOf(9, 40, 200)).map { Race(it.first.toLong(), it.second.toLong()) }

    @Test
    fun part1() {

        assertEquals(Race(7, 9), input[0])
        assertEquals(Race(15, 40), input[1])
        assertEquals(Race(30, 200), input[2])

        assertEquals(0, input[0].distance(0))
        assertEquals(6, input[0].distance(1))
        assertEquals(10, input[0].distance(2))
        assertEquals(12, input[0].distance(3))
        assertEquals(12, input[0].distance(4))
        assertEquals(10, input[0].distance(5))
        assertEquals(6, input[0].distance(6))
        assertEquals(0, input[0].distance(7))


        val waysToWin0 = (0..input[0].time).map { input[0].distance(it) }.count { it > input[0].distance }
        assertEquals(4, waysToWin0)

        val waysToWin1 = (0..input[1].time).map { input[1].distance(it) }.count { it > input[1].distance }
        assertEquals(8, waysToWin1)

        val waysToWin2 = (0..input[2].time).map { input[2].distance(it) }.count { it > input[2].distance }
        assertEquals(9, waysToWin2)

        val total = input.fold(1) { total, race -> (0..race.time).map { race.distance(it) }.count { it > race.distance } * total }
        assertEquals(288, total)
    }

    @Test
    fun part2() {
        val race = Race(71530, 940200)
        val total = (0..race.time).map { race.distance(it) }.count { it > race.distance }
        assertEquals(71503, total)
    }
}
