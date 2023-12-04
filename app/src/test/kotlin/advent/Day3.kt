package advent.test

import advent.day3.Coordinate
import advent.day3.Map
import advent.day3.Number
import advent.day3.Symbol
import advent.day3.Gear
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class Day3 {
    private val input = listOf(
            "467..114..",
            "...*......",
            "..35..633.",
            "......#...",
            "617*......",
            ".....+.58.",
            "..592.....",
            "......755.",
            "...$.*....",
            ".664.598..")

    @Test
    fun part1() {
        val map = Map.parse(input)

        assertEquals(10, map.numbers.size)
        assertEquals(6, map.symbols.size)

        assertEquals(Number(467, Coordinate(0, 0), Coordinate(2, 0)), map.numbers[0])
        assertEquals(Number(35, Coordinate(2, 2), Coordinate(3, 2)), map.numbers[2])

        assertEquals(Symbol('*', Coordinate(3, 1)), map.symbols[0])
        assertEquals(Symbol('#', Coordinate(6, 3)), map.symbols[1])

        assertTrue(map.isPartNumber(map.numbers[0]))

        assertEquals(Number(114, Coordinate(5, 0), Coordinate(7, 0)), map.numbers[1])
        assertFalse(map.isPartNumber(map.numbers[1]))

        assertTrue(map.isPartNumber(map.numbers[2]))
        assertEquals(Number(58, Coordinate(7, 5), Coordinate(8, 5)), map.numbers[5])

        assertFalse(map.isPartNumber(map.numbers[5]))

        val sumOfPartNumbers = map.numbers.filter { number -> map.isPartNumber(number) }
                .sumOf { number -> number.value }
        assertEquals(4361, sumOfPartNumbers)
    }

    @Test
    fun part2() {
        val map = Map.parse(input)

        val gears = map.gears()
        assertEquals(Gear(Coordinate(3, 1), 467, 35), gears[0])
        assertEquals(16345, gears[0].ratio())
        assertEquals(Gear(Coordinate(5, 8), 755, 598), gears[1])
        assertEquals(451490, gears[1].ratio())

        val sumOfGeartRatios = gears.sumOf { gear -> gear.ratio() }
        assertEquals(467835, sumOfGeartRatios)
    }
}
