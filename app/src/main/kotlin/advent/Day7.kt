package advent.day7

import java.io.File

enum class Card(val which: Char) {
    ONE('1'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    TEN('T'),
    JACK('J'),
    QUEEN('Q'),
    KING('K'),
    ACE('A');

    fun rank(jokers: Boolean = false): Int {
        return if (!jokers || this != Card.JACK) {
            ordinal
        } else {
            Card.ONE.ordinal
        }
    }

    companion object {
        private val map = entries.associateBy { it.which }
        infix fun from(value: Char) = map[value]
    }
}

enum class Combination(val which: Int) {
    HIGHEST_CARD(1),
    ONE_PAIR(2),
    TWO_PAIRS(3),
    THREE_OF_A_KIND(4),
    FULL_HOUSe(5),
    FOUR_OF_A_KIND(6),
    FIVE_OF_A_KIND(7);
}

fun majorRank(frequencies: List<Int>): Combination {
    val isFiveOfAkind = frequencies[0] == 5
    val isFourOfAkind = frequencies[0] == 4
    val isFullHouse = frequencies.size >= 2 && frequencies[0] == 3 && frequencies[1] == 2
    val isThreeOfAKind = frequencies[0] == 3
    val isTwoPairs = frequencies.size >= 2 && frequencies[0] == 2 && frequencies[1] == 2
    val isOnePair = frequencies[0] == 2

    return when {
        isFiveOfAkind -> Combination.FIVE_OF_A_KIND
        isFourOfAkind -> Combination.FOUR_OF_A_KIND
        isFullHouse -> Combination.FULL_HOUSe
        isThreeOfAKind -> Combination.THREE_OF_A_KIND
        isTwoPairs -> Combination.TWO_PAIRS
        isOnePair -> Combination.ONE_PAIR
        else -> Combination.HIGHEST_CARD
    }
}

data class HandPart1(val cards: List<Card>, val bid: Long) : Comparable<HandPart1> {

    private val frequencies = cards.groupingBy { it }.eachCount().values.sortedDescending()

    val majorRank = majorRank(frequencies)

    override fun compareTo(other: HandPart1): Int {
        val major = compareMajorRank(other)

        return if (major != 0) {
            major
        } else {
            compareMinorRank(other)
        }
    }

    private fun compareMajorRank(other: HandPart1): Int {
        return majorRank(this.frequencies) compareTo majorRank(other.frequencies)
    }

    private fun compareMinorRank(other: HandPart1): Int {
        this.cards.forEachIndexed { index, value ->
            if (value != other.cards[index]) {
                return value.rank() compareTo other.cards[index].rank()
            }
        }
        return 0
    }
}

data class HandPart2(val cards: List<Card>, val bid: Long) : Comparable<HandPart2> {

    private val frequencies = applyJokers(cards).groupingBy { it }.eachCount().values.sortedDescending()

    val majorRank = majorRank(frequencies)

    override fun compareTo(other: HandPart2): Int {
        val major = compareMajorRank(other)

        return if (major != 0) {
            major
        } else {
            compareMinorRank(other)
        }
    }

    private fun compareMajorRank(other: HandPart2): Int {
        return majorRank(this.frequencies) compareTo majorRank(other.frequencies)
    }

    private fun compareMinorRank(other: HandPart2): Int {
        this.cards.forEachIndexed { index, value ->
            if (value != other.cards[index]) {
                return value.rank(true) compareTo other.cards[index].rank(true)
            }
        }
        return 0
    }

    private fun applyJokers(cards: List<Card>): List<Card> {

        val filteredCards = cards.filter { it != Card.JACK }
        val mostFrequentCard = if (filteredCards.isNotEmpty()) {
            filteredCards.groupingBy { it }
                    .eachCount()
                    .maxBy { it.value }
                    .key
        } else {
            null
        }

        return cards.map {
            if (mostFrequentCard != null && it == Card.JACK) {
                mostFrequentCard
            } else {
                it
            }
        }
    }
}

fun parse(line: String): Pair<List<Card>, Long> {
    val tokens = line.split(' ')

    return Pair(tokens[0].map { (Card from it)!! }, tokens[1].toLong())
}

fun totalWinningsPart1(hands: List<HandPart1>): Long {
    return hands.sorted().foldIndexed(0L) { index, acc, hand -> acc + (index + 1) * hand.bid }
}

fun totalWinningsPart2(hands: List<HandPart2>): Long {
    return hands.sorted().foldIndexed(0L) { index, acc, hand -> acc + (index + 1) * hand.bid }
}

class Day7 {
    fun part1() {
        val hands = File("app/src/main/kotlin/advent/input/day7.txt").readLines().map { parse(it) }.map { HandPart1(it.first, it.second) }
        println("Day 7 Part 1 : ${totalWinningsPart1(hands)}")
    }

    fun part2() {
        val hands = File("app/src/main/kotlin/advent/input/day7.txt").readLines().map { parse(it) }.map { HandPart2(it.first, it.second) }
        println("Day 7 Part 2 : ${totalWinningsPart2(hands)}")
    }
}

fun run() {
    Day7().part1()
    Day7().part2()
}