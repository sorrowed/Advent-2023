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

        val sum = input.sumOf { pattern: List<String> -> Pattern(pattern).summarize() }

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
}
