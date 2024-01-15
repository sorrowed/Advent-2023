package advent

import advent.day15.BoxArray
import advent.day15.Hash
import advent.day15.Step
import kotlin.test.Test
import kotlin.test.assertEquals

class Day15 {

    val input = listOf(
            "rn=1",
            "cm-",
            "qp=3",
            "cm=2",
            "qp-",
            "pc=4",
            "ot=9",
            "ab=5",
            "pc-",
            "pc=6",
            "ot=7")

    @Test
    fun `given HASH when hash is calculated then result is correct`() {
        assertEquals(52, Hash(listOf("HASH")).hash())
    }

    @Test
    fun `given input when hash is calculated then result is correct`() {
        assertEquals(1320, Hash(input).hash())
    }

    @Test
    fun `given input when HASHMAP is run then resulting boxes and focal power are correct`() {

        val array = BoxArray(input.map { Step.parse(it) })

        assertEquals(listOf(Step("rn", '=', 1), Step("cm", '=', 2)), array.boxes[0]!!.toList())
        assertEquals(listOf(Step("ot", '=', 7), Step("ab", '=', 5), Step("pc", '=', 6)), array.boxes[3]!!.toList())

        assertEquals(145, array.focalPower())
    }
}    