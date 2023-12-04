package advent.day2

import java.io.File

data class Hand(val red: Int, val green: Int, val blue: Int) {
    fun power(): Int {
        return if (red != 0) {
            red
        } else {
            1
        } * if (green != 0) {
            green
        } else {
            1
        } * if (blue != 0) {
            blue
        } else {
            1
        }
    }
}

class Game(val id: Int) {
    var hands = mutableListOf<Hand>()

    fun add(hand: Hand) {
        hands.add(hand)
    }

    fun max(): Hand {
        return hands.fold(Hand(0, 0, 0)) { total, hand -> Hand(maxOf(total.red, hand.red), maxOf(total.green, hand.green), maxOf(total.blue, hand.blue)) }
    }

    companion object {
        fun parse(source: String): Game {
            val gameRe = """Game (\d*): (.*)""".toRegex()
            val gameMatch = gameRe.matchEntire(source)!!

            val game = Game(gameMatch.groupValues[1].toInt())

            val handsRe = """(\d*) (blue|red|green)""".toRegex()
            val hands = gameMatch.groupValues[2].split(";")

            for (token in hands) {
                val handMatches = handsRe.findAll(token)

                var red = 0
                var green = 0
                var blue = 0

                for (match in handMatches) {

                    val count = match.groups[1]!!.value.toInt()
                    when (val color = match.groups[2]!!.value) {
                        "red" -> count.also { red = it }
                        "green" -> count.also { green = it }
                        "blue" -> count.also { blue = it }
                        else -> throw Exception("Unsupported color in input $color")
                    }
                }

                game.add(Hand(red, green, blue))
            }
            return game
        }
    }
}


fun MutableList<Game>.filterMaxCubesAndSum(red: Int, green: Int, blue: Int): Int {
    val possibleGames = this.filter { game ->
        val max = game.max()
        max.red <= red && max.green <= green && max.blue <= blue
    }
    return possibleGames.sumOf { game -> game.id }
}


class Day2 {
    fun part1() {
        val games = File("app/src/main/kotlin/advent/input/day2.txt")
                .readLines().fold(mutableListOf<Game>()) { games, line ->
                    games.add(Game.parse(line))
                    games
                }

        val sumOfIds = games.filterMaxCubesAndSum(12, 13, 14)

        println("Day 2 Part 1 : $sumOfIds")
    }

    fun part2() {
        val games = File("app/src/main/kotlin/advent/input/day2.txt")
                .readLines().fold(mutableListOf<Game>()) { games, line ->
                    games.add(Game.parse(line))
                    games
                }

        val sumOfPower = games.sumOf { game -> game.max().power() }

        println("Day 2 Part 2 : $sumOfPower")
    }
}

fun run() {
    Day2().part1()
    Day2().part2()
}

