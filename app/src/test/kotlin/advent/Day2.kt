package advent.test

import advent.day2.Game
import advent.day2.Hand
import advent.day2.filterMaxCubesAndSum
import kotlin.test.Test
import kotlin.test.assertEquals

class Day2 {
    private val input = listOf("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
            "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
            "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
            "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
            "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green")

    @Test
    fun part1() {

        val games = mutableListOf<Game>()

        for (line in input) {
            games.add(Game.parse(line))
        }
        assertEquals(5, games.size)

        val game1 = games[0]
        assertEquals(1, game1.id)

        assertEquals(game1.hands.size, 3)

        val h1 = game1.hands[0]
        assertEquals(h1.red, 4)
        assertEquals(h1.green, 0)
        assertEquals(h1.blue, 3)

        val h2 = game1.hands[1]
        assertEquals(h2.red, 1)
        assertEquals(h2.green, 2)
        assertEquals(h2.blue, 6)

        val maxCubes = games.maxCubes()

        assertEquals(Hand(20, 13, 15), maxCubes)

        val sumOfIds = games.filterMaxCubesAndSum(12, 13, 14)
        assertEquals(8, sumOfIds)

    }

    @Test
    fun part2() {
        val games = input.map { Game.parse(it) }

        assertEquals(5, games.size)

        assertEquals(Hand(4, 2, 6), games[0].max())
        assertEquals(Hand(1, 3, 4), games[1].max())
        assertEquals(Hand(20, 13, 6), games[2].max())
        assertEquals(Hand(14, 3, 15), games[3].max())
        assertEquals(Hand(6, 3, 2), games[4].max())

        val sumOfPower = games.sumOf { game -> game.max().power() }

        assertEquals(2286, sumOfPower)
    }
}

fun MutableList<Game>.maxCubes(): Hand {
    return this.fold(
            Hand(0, 0, 0))
    { cubes, game ->
        val m = game.max()
        Hand(maxOf(cubes.red, m.red), maxOf(cubes.green, m.green), maxOf(cubes.blue, m.blue))
    }
}
