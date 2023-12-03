package advent.day1

import java.io.File

class Day1 {
    private val digits = listOf(
            Pair("1", "one"),
            Pair("2", "two"),
            Pair("3", "three"),
            Pair("4", "four"),
            Pair("5", "five"),
            Pair("6", "six"),
            Pair("7", "seven"),
            Pair("8", "eight"),
            Pair("9", "nine"))

    private fun firstAndLastOrdinal(line: String): Int {
        val first = line.first { c -> c.isDigit() }.digitToInt()
        val last = line.reversed().first { c -> c.isDigit() }.digitToInt()

        return 10 * first + last
    }

    private fun replaceWrittenDigits(line: String): String {

        var source = line
        var result = ""

        while (source.isNotEmpty()) {

            var writtenDigit = false
            for (digit in digits) {
                if (source.startsWith(digit.second)) {
                    result += digit.first
                    source = source.drop(digit.second.length - 1)
                    writtenDigit = true
                    break
                }
            }

            if (!writtenDigit) {
                result += source[0]
                source = source.drop(1)
            }
        }

        return result
    }

    fun part1Sum(lines: List<String>): Int {
        return lines.fold(0) { sum, line -> sum + firstAndLastOrdinal(line) }
    }

    fun part2Sum(lines: List<String>): Int {
        return lines.fold(0) { sum, line -> sum + firstAndLastOrdinal(replaceWrittenDigits(line)) }
    }

    fun part1() {

        val total = part1Sum(File("app/src/main/kotlin/advent/input/day1.txt")
                .readLines())
        print("Day1 Part1 : $total\n")
    }

    fun part2() {
        val total = part2Sum(File("app/src/main/kotlin/advent/input/day1.txt")
                .readLines())

        print("Day1 Part2 : $total\n")
    }
}

fun run() {
    Day1().part1()
    Day1().part2()
}
