package advent

import advent.day18.Digplan
import advent.support.Position
import advent.support.area
import advent.support.perimeter
import advent.support.pointsInside
import kotlin.test.Test
import kotlin.test.assertEquals

class Day18 {
    val input = listOf(
            "R 6 (#70c710)",
            "D 5 (#0dc571)",
            "L 2 (#5713f0)",
            "D 2 (#d2c081)",
            "R 2 (#59c680)",
            "D 2 (#411b91)",
            "L 5 (#8ceee2)",
            "U 2 (#caa173)",
            "L 1 (#1b58a2)",
            "U 2 (#caa171)",
            "R 2 (#7807d2)",
            "U 3 (#a77fa3)",
            "L 2 (#015232)",
            "U 2 (#7a21e3)")

    private val digplan = Digplan.parse(input)

    @Test
    fun `given digplan when executed then result is correct`() {

        val border = digplan.border

        assertEquals(38.0, perimeter(border))
    }

    @Test
    fun `given vertices when area calcuated then result is correct`() {

        val border = setOf(Position(4, 10), Position(15, 8), Position(11, 2), Position(2, 2))

        assertEquals(73, area(border))
    }

    @Test
    fun `given vertices when area calcuated then result is correct 2`() {

        val border = setOf(Position(0, 0), Position(6, 0), Position(6, 3), Position(0, 3))

        assertEquals(7 + 4 + 7 + 4 - border.size, perimeter(border).toInt())
        assertEquals(28, pointsInside(area(border), perimeter(border).toLong()) + perimeter(border).toLong())
    }

    @Test
    fun `given digplan when dug out then result is correct`() {

        val border = digplan.border
        val perimeter = perimeter(border).toLong()

        assertEquals(62, pointsInside(area(border), perimeter) + perimeter)
    }

    @Test
    fun `given revised digplan when dug out then result is correct`() {

        val plan = Digplan.parseRevised(input)

        assertEquals(952408144115L, plan.area())
    }
}
