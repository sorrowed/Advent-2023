package advent

import advent.day13.Pattern
import kotlin.test.Test
import kotlin.test.assertEquals

class Day13 {

    val patterns = listOf(
            "#.##..##.",
            "..#.##.#.",
            "##......#",
            "##......#",
            "..#.##.#.",
            "..##..##.",
            "#.#.##.#.",
            "",
            "#...##..#",
            "#....#..#",
            "..##..###",
            "#####.##.",
            "#####.##.",
            "..##..###",
            "#....#..#")

    private val input = patterns
            .joinToString("\n")
            .split("\n\n")
            .map { it.split("\n") }

    @Test
    fun `given input correct groups are found`() {

        assertEquals(listOf(
                listOf("#.##..##.",
                        "..#.##.#.",
                        "##......#",
                        "##......#",
                        "..#.##.#.",
                        "..##..##.",
                        "#.#.##.#."
                ),
                listOf("#...##..#",
                        "#....#..#",
                        "..##..###",
                        "#####.##.",
                        "#####.##.",
                        "..##..###",
                        "#....#..#")), input)
    }

    @Test
    fun `given first pattern correct column reflections are found`() {

        val reflected = Pattern(input[0]).reflectedColumns()

        assertEquals(listOf(5), reflected)
    }

    @Test
    fun `given second pattern correct row reflections are found`() {
        val reflected = Pattern(input[1]).reflectedRows()

        assertEquals(listOf(4), reflected)
    }

    @Test
    fun `given patterns correct summarize total`() {

        val sum = input.sumOf { pattern: List<String> -> Pattern(pattern).summarizeReflected() }

        assertEquals(405, sum)
    }

    @Test
    fun `given some fabricated input check if result is correct`() {

        val t = listOf("##", "##")

        val c = Pattern(t).reflectedColumns()

        assertEquals(listOf(1), c)

        val r = Pattern(t).reflectedRows()
        assertEquals(listOf(1), r)
    }

    @Test
    fun `given first pattern when inspected determine that row 3 (4) is smudged row reflection line`() {

        assertEquals(false, Pattern.rowSmudged(input[0], 0))
        assertEquals(false, Pattern.rowSmudged(input[0], 1))
        assertEquals(false, Pattern.rowSmudged(input[0], 2))
        assertEquals(true, Pattern.rowSmudged(input[0], 3))
        assertEquals(false, Pattern.rowSmudged(input[0], 4))
        assertEquals(false, Pattern.rowSmudged(input[0], 5))
        assertEquals(false, Pattern.rowSmudged(input[0], 6))
    }

    @Test
    fun `given second pattern when inspected determine that row 1 (2) is smudged row reflection line`() {

        assertEquals(false, Pattern.rowSmudged(input[1], 0))
        assertEquals(true, Pattern.rowSmudged(input[1], 1))
        assertEquals(false, Pattern.rowSmudged(input[1], 2))
        assertEquals(false, Pattern.rowSmudged(input[1], 3))
        assertEquals(false, Pattern.rowSmudged(input[1], 4))
        assertEquals(false, Pattern.rowSmudged(input[1], 5))
        assertEquals(false, Pattern.rowSmudged(input[1], 6))
    }

    @Test
    fun `given patterns correct smudged summarize total`() {

        val sum = input.sumOf { pattern: List<String> -> Pattern(pattern).summarizeSmudged() }

        assertEquals(400, sum)
    }
}
