package advent

import kotlin.test.Test
import kotlin.test.assertEquals
import advent.day4.Card

class Day4 {
    private val input = listOf(
            "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
            "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
            "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
            "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
            "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
            "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11")

    @Test
    fun part1() {
        val cards = input.map { Card.parse(it) }

        assertEquals(6, cards.size)

        assertEquals(1, cards[0].id)
        assertEquals(5, cards[0].winning.size)
        assertEquals(8, cards[0].numbers.size)

        assertEquals(2, cards[1].id)
        assertEquals(5, cards[1].winning.size)
        assertEquals(8, cards[1].numbers.size)

        val winning = cards.map() { card ->
            card.winningNumbers().size
        }
        assertEquals(6, winning.size)

        assertEquals(4, winning[0])
        assertEquals(2, winning[1])
        assertEquals(2, winning[2])
        assertEquals(1, winning[3])
        assertEquals(0, winning[4])
        assertEquals(0, winning[5])

        assertEquals(8, cards[0].score())
        assertEquals(2, cards[1].score())
        assertEquals(2, cards[2].score())
        assertEquals(1, cards[3].score())
        assertEquals(0, cards[4].score())
        assertEquals(0, cards[5].score())

        assertEquals(13, cards.sumOf { card -> card.score() })
    }

    @Test
    fun part2() {
        val cards = input.map { Pair(1, Card.parse(it)) }.toMutableList()

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
        assertEquals(30, totalNumberOfCards)
    }
}
