package advent

import advent.day14.Platform
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class Day14 {

    val input = listOf(
            "O....#....",
            "O.OO#....#",
            ".....##...",
            "OO.#O....O",
            ".O.....O#.",
            "O.#..O.#.#",
            "..O..#O..O",
            ".......O..",
            "#....###..",
            "#OO..#...."
    )

    @Test
    fun `given platform when tilted north then total load is correct`() {

        val platform = Platform(input).tiltNorth()

        println(input.forEach(::println))
        println(platform.rocks.forEach(::println))

        assertEquals(listOf(
                "OOOO.#.O..",
                "OO..#....#",
                "OO..O##..O",
                "O..#.OO...",
                "........#.",
                "..#....#.#",
                "..O..#.O.O",
                "..O.......",
                "#....###..",
                "#....#...."), platform.rocks)

        assertEquals(136, platform.totalLoad())
    }

    @Test
    fun `given platform when cycled then rocks are in correct position`() {

        var platform = Platform(input).cycle()

        assertEquals(listOf(
                ".....#....",
                "....#...O#",
                "...OO##...",
                ".OO#......",
                ".....OOO#.",
                ".O#...O#.#",
                "....O#....",
                "......OOOO",
                "#...O###..",
                "#..OO#...."), platform.rocks)

        platform = platform.cycle()

        assertEquals(listOf(
                ".....#....",
                "....#...O#",
                ".....##...",
                "..O#......",
                ".....OOO#.",
                ".O#...O#.#",
                "....O#...O",
                ".......OOO",
                "#..OO###..",
                "#.OOO#...O"), platform.rocks)

        platform = platform.cycle()

        assertEquals(listOf(
                ".....#....",
                "....#...O#",
                ".....##...",
                "..O#......",
                ".....OOO#.",
                ".O#...O#.#",
                "....O#...O",
                ".......OOO",
                "#...O###.O",
                "#.OOO#...O"), platform.rocks)
    }

    @Test
    fun `given platform when cycled 1000000000 times then total load is correct`() {

        val platform = Platform(input).cycle(1_000_000_000)

        assertEquals(64, platform.totalLoad())
    }

    @Test
    fun `given platform when cycled 1000000000 times with real input then total load is 83790`() {

        val realInput = File("/home/tom/Projects/Advent-2023/app/src/main/kotlin/advent/input/day14.txt").readLines()

        val platform = Platform(realInput).cycle(1_000_000_000)

        assertEquals(83790, platform.totalLoad())
    }
}
