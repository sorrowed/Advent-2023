package advent

import  advent.day12.groupsAndTemplate
import  advent.day12.arrangements

import kotlin.test.Test
import kotlin.test.assertEquals

class Day12 {

    val input = listOf(
            "???.### 1,1,3",
            ".??..??...?##. 1,1,3",
            "?#?#?#?#?#?#?#? 1,3,1,6",
            "????.#...#... 4,1,1",
            "????.######..#####. 1,6,5",
            "?###???????? 3,2,1")

    @Test
    fun `given a string when parsed then a result is valid`() {

        assertEquals("???.###" to listOf(1, 1, 3), groupsAndTemplate(input[0]))
        assertEquals(".??..??...?##." to listOf(1, 1, 3), groupsAndTemplate(input[1]))
        assertEquals("?#?#?#?#?#?#?#?" to listOf(1, 3, 1, 6), groupsAndTemplate(input[2]))
        assertEquals("????.#...#..." to listOf(4, 1, 1), groupsAndTemplate(input[3]))
    }
}
