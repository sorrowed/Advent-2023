package advent

import advent.day7.*
import kotlin.test.*

class Day7 {

    private val input = listOf("32T3K 765", "T55J5 684", "KK677 28", "KTJJT 220", "QQQJA 483")

    @Test
    fun part1() {
        val hands = input
                .map { line -> parse(line) }.map { HandPart1(it.first, it.second) }

        assertEquals(5, hands.count())

        assertContentEquals(listOf(Card.THREE, Card.TWO, Card.TEN, Card.THREE, Card.KING), hands[0].cards)
        assertEquals(765, hands[0].bid)

        assertEquals(Combination.ONE_PAIR, hands[0].majorRank)
        assertEquals(Combination.THREE_OF_A_KIND, hands[1].majorRank)
        assertEquals(Combination.TWO_PAIRS, hands[2].majorRank)
        assertEquals(Combination.TWO_PAIRS, hands[3].majorRank)
        assertEquals(Combination.THREE_OF_A_KIND, hands[4].majorRank)

        assertTrue(hands[0] < hands[1])
        assertTrue(hands[1] > hands[2])
        assertTrue(hands[2] > hands[3])

        assertTrue(hands[3] < hands[4])

        val ranks = hands.map { it }.sorted() // Low to high
        assertTrue(ranks[0] == hands[0]) // One pair
        assertTrue(ranks[1] == hands[3]) // Two pairs, T first
        assertTrue(ranks[2] == hands[2]) // Two pairs, K first
        assertTrue(ranks[3] == hands[1]) // Three of a kind, T first
        assertTrue(ranks[4] == hands[4]) // Three of a kind, Q first

        val totalWinnings = totalWinningsPart1(hands)
        assertEquals(6440, totalWinnings)
    }

    @Test
    fun part2() {
        val hands = input
                .map { line -> parse(line) }.map { HandPart2(it.first, it.second) }

        assertEquals(Combination.ONE_PAIR, hands[0].majorRank)
        assertEquals(Combination.FOUR_OF_A_KIND, hands[1].majorRank)
        assertEquals(Combination.TWO_PAIRS, hands[2].majorRank)
        assertEquals(Combination.FOUR_OF_A_KIND, hands[3].majorRank)
        assertEquals(Combination.FOUR_OF_A_KIND, hands[4].majorRank)

        val totalWinnings = totalWinningsPart2(hands)
        assertEquals(5905, totalWinnings)
    }
}
