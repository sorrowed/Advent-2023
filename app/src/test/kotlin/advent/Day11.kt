package advent

import advent.day11.*
import advent.support.Position
import advent.support.pairs
import kotlin.test.Test
import kotlin.test.assertEquals

class Day11 {

    val input = listOf(
            "...#......",
            ".......#..",
            "#.........",
            "..........",
            "......#...",
            ".#........",
            ".........#",
            "..........",
            ".......#..",
            "#...#.....")


    @Test
    fun `given input when parsed to universe then result is valid`() {
        val map = Universe.parse(input)
        assertEquals(Tile.GALAXY, map.tiles[Position(3, 0)])
        assertEquals(Tile.EMPTY, map.tiles[Position(0, 8)])
        assertEquals(Tile.GALAXY, map.tiles[Position(0, 9)])
        assertEquals(Tile.EMPTY, map.tiles[Position(9, 9)])
    }

    @Test
    fun `given input when expanded then is expected`() {
        val expanded = expandInput(input)

        val expected = listOf(
                "....#........",
                ".........#...",
                "#............",
                ".............",
                ".............",
                "........#....",
                ".#...........",
                "............#",
                ".............",
                ".............",
                ".........#...",
                "#....#.......")

        assertEquals(expected, expanded)
    }

    @Test
    fun `given parsed expanded universe when galaxies extracted then is expected`() {

        val galaxies = Universe.parse(expandInput(input)).galaxies()
        assertEquals(Position(4, 0), galaxies[0])
        assertEquals(Position(12, 7), galaxies[5])
        assertEquals(Position(0, 11), galaxies[7])

        val pairs = galaxies.pairs()

        assertEquals(15, galaxies[0].manhattan(galaxies[6]))
        assertEquals(17, galaxies[2].manhattan(galaxies[5]))
        assertEquals(5, galaxies[7].manhattan(galaxies[8]))

        assertEquals(374, pairs.sumOf { it.first.manhattan(it.second) })
    }

    @Test
    fun `given alternative way of expanding result is valid`() {

        val (emptyRows, emptyColumns) = emptyRowsAndColumns(input)
        val galaxyPairs = Universe.parse(input).galaxies().pairs()

        val crossed = galaxyPairs.map { it to crossedRowsAndColumns(it, emptyRows, emptyColumns) }
                .associate { pair -> pair.first to pair.second }


        assertEquals(374, galaxyPairs.sumOf { it.first.manhattan(it.second) + (crossed[it]!! * 1) })
        assertEquals(1030, galaxyPairs.sumOf { it.first.manhattan(it.second) + (crossed[it]!! * 9) })
        assertEquals(8410, galaxyPairs.sumOf { it.first.manhattan(it.second) + (crossed[it]!! * 99) })
    }
}