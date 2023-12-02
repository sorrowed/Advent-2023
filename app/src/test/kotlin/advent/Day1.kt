package advent.Test

import advent.day1.Day1
import kotlin.test.Test
import kotlin.test.assertEquals

class Day1 {
    @Test
    fun part1() {
        val input = listOf("1abc2",
                "pqr3stu8vwx",
                "a1b2c3d4e5f",
                "treb7uchet")

        val total = Day1().part1Sum(input)

        assertEquals(142, total)
    }

    @Test
    fun part2() {
        val lines = listOf("two1nine",
                "eightwothree",
                "abcone2threexyz",
                "xtwone3four",
                "4nineeightseven2",
                "zoneight234",
                "7pqrstsixteen")

        val total = Day1().part2Sum(lines)
        assertEquals(281, total)
    }
}
