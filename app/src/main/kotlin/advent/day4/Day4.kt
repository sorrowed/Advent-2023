package advent.day4

import java.io.File

data class Card(val id: Int, val winning: List<Int>, val numbers: List<Int>) {

    fun winningNumbers() = numbers.filter { n -> winning.contains(n) }

    fun score() = winningNumbers().fold(0) { sum, _ ->
        if (sum == 0) {
            1
        } else {
            sum * 2
        }
    }

    companion object {
        fun parse(input: String): Card {

            val tokens = input.split(':', '|')

            return Card(
                    tokens[0].split(' ')
                            .filter { token -> token.isNotEmpty() }[1].toInt(),
                    tokens[1].split(' ')
                            .filter { token -> token.isNotEmpty() }
                            .map { token -> token.toInt() }
                            .toList(),
                    tokens[2].split(' ')
                            .filter { token -> token.isNotEmpty() }
                            .map { token -> token.toInt() }
                            .toList(),
            )
        }
    }
}

class Day4 {
    fun part1() {
        val cards = File("app/src/main/kotlin/advent/input/day4.txt")
                .readLines()
                .fold(mutableListOf<Card>()) { c, line ->
                    c.add(Card.parse(line))
                    c
                }

        val totalScore = cards.sumOf { card -> card.score() }
        println("Day 4 Part 1 : $totalScore")
    }

    fun part2() {
        val cards = File("app/src/main/kotlin/advent/input/day4.txt")
                .readLines()
                .fold(mutableListOf<Pair<Int, Card>>()) { c, line ->
                    c.add(Pair(1, Card.parse(line)))
                    c
                }

        cards.forEachIndexed() { index, hand ->
            val numberOfCardsWon = hand.second.winningNumbers().size;

            for (i in 1..numberOfCardsWon) {
                if (index + i < cards.size) {

                    val nextHand = cards[index + i]

                    cards[index + i] = Pair(nextHand.first + hand.first, nextHand.second)
                }
            }
        }

        val totalNumberOfCards = cards.sumOf { card -> card.first }
        println("Day 4 Part 2 : $totalNumberOfCards")
    }
}

fun run() {
    Day4().part1()
    Day4().part2()
}

