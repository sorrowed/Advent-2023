package advent

import advent.day8.DesertMap
import kotlin.test.*

class Day8 {

    @Test
    fun part1() {
        val input1 = listOf(
                "RL",
                "",
                "AAA = (BBB, CCC)",
                "BBB = (DDD, EEE)",
                "CCC = (ZZZ, GGG)",
                "DDD = (DDD, DDD)",
                "EEE = (EEE, EEE)",
                "GGG = (GGG, GGG)",
                "ZZZ = (ZZZ, ZZZ)")

        val map1 = DesertMap.parse(input1)
        assertEquals(2, map1.traversePart1())

        val input2 = listOf(
                "LLR",
                "",
                "AAA = (BBB, BBB)",
                "BBB = (AAA, ZZZ)",
                "ZZZ = (ZZZ, ZZZ)")

        val map2 = DesertMap.parse(input2)
        assertEquals(6, map2.traversePart1())
    }

    @Test
    fun part2() {

        var input = listOf(
                "LR",
                "",
                "11A = (11B, XXX)",
                "11B = (XXX, 11Z)",
                "11Z = (11B, XXX)",
                "22A = (22B, XXX)",
                "22B = (22C, 22C)",
                "22C = (22Z, 22Z)",
                "22Z = (22B, 22B)",
                "XXX = (XXX, XXX)")

        val map = DesertMap.parse(input)
        assertEquals(6, map.traversePart2())
        assertEquals(6, map.traversePart3())
    }
}
