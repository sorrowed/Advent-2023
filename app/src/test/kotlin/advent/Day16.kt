package advent

import kotlin.test.Test
import kotlin.test.assertEquals
import advent.day16.Contraption
import advent.day16.Direction
import advent.support.Position

class Day16 {

    val input = listOf(
            """.|...\....""",
            """|.-.\.....""",
            """.....|-...""",
            """........|.""",
            """..........""",
            """.........\""",
            """..../.\\..""",
            """.-.-/..|..""",
            """.|....-|.\""",
            """..//.|....""")

    @Test
    fun `given input when parsed then contraption is correct`() {

        val contraption = Contraption.parse(input)

        val beams = contraption.trail(Position(0, 0), Direction.RIGHT)

        Contraption.print(contraption, beams)

        assertEquals(46, beams.distinctBy { it.first }.count())
    }

}