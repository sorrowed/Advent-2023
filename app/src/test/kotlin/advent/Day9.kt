package advent

import advent.day9.Day9
import kotlin.test.*

class Day9 {

    val input = listOf(
            "0 3 6 9 12 15",
            "1 3 6 10 15 21",
            "10 13 16 21 30 45")

    @Test
    fun part1() {
        val derived1 = Day9().derive(input[0].split(' ').map { it.toInt() })
        assertContentEquals(listOf(3, 3, 3, 3, 3), derived1[1])
        assertContentEquals(listOf(0, 0, 0, 0), derived1[2])

        val extrapolated1 = Day9().extrapolateForwards(derived1)
        assertContentEquals(listOf(0, 3, 6, 9, 12, 15, 18), extrapolated1[0])
        assertContentEquals(listOf(3, 3, 3, 3, 3, 3), extrapolated1[1])
        assertContentEquals(listOf(0, 0, 0, 0, 0), extrapolated1[2])

        val derived2 = Day9().derive(input[1].split(' ').map { it.toInt() })
        assertContentEquals(listOf(1, 3, 6, 10, 15, 21), derived2[0])
        assertContentEquals(listOf(2, 3, 4, 5, 6), derived2[1])
        assertContentEquals(listOf(1, 1, 1, 1), derived2[2])
        assertContentEquals(listOf(0, 0, 0), derived2[3])

        val extrapolated2 = Day9().extrapolateForwards(derived2)
        assertContentEquals(listOf(1, 3, 6, 10, 15, 21, 28), extrapolated2[0])
        assertContentEquals(listOf(2, 3, 4, 5, 6, 7), extrapolated2[1])
        assertContentEquals(listOf(1, 1, 1, 1, 1), extrapolated2[2])
        assertContentEquals(listOf(0, 0, 0, 0), extrapolated2[3])

        val derived3 = Day9().derive(input[2].split(' ').map { it.toInt() })
        assertContentEquals(listOf(10, 13, 16, 21, 30, 45), derived3[0])
        assertContentEquals(listOf(3, 3, 5, 9, 15), derived3[1])
        assertContentEquals(listOf(0, 2, 4, 6), derived3[2])
        assertContentEquals(listOf(2, 2, 2), derived3[3])
        assertContentEquals(listOf(0, 0), derived3[4])

        val extrapolated3 = Day9().extrapolateForwards(derived3)
        assertContentEquals(listOf(10, 13, 16, 21, 30, 45, 68), extrapolated3[0])
        assertContentEquals(listOf(3, 3, 5, 9, 15, 23), extrapolated3[1])
        assertContentEquals(listOf(0, 2, 4, 6, 8), extrapolated3[2])
        assertContentEquals(listOf(2, 2, 2, 2), extrapolated3[3])
        assertContentEquals(listOf(0, 0, 0), extrapolated3[4])

        val sumOfInterPolated = extrapolated1[0].last() + extrapolated2[0].last() + extrapolated3[0].last()
        assertEquals(114, sumOfInterPolated)
    }

    @Test
    fun part2() {
        val derived3 = Day9().derive(input[2].split(' ').map { it.toInt() })
        val extrapolated3 = Day9().extrapolateBackwards(derived3)
        assertContentEquals(listOf(5, 10, 13, 16, 21, 30, 45), extrapolated3[0])
        assertContentEquals(listOf(5, 3, 3, 5, 9, 15), extrapolated3[1])
        assertContentEquals(listOf(-2, 0, 2, 4, 6), extrapolated3[2])
        assertContentEquals(listOf(2, 2, 2, 2), extrapolated3[3])
        assertContentEquals(listOf(0, 0, 0), extrapolated3[4])

    }
}

        

