package advent

import advent.support.Position
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.expect

//import advent.day12.*

enum class SpringCondition(val condition: Char) {

    OPERATIONAL('.'),
    BROKEN('#'),
    UNKNOWN('?');

    companion object {
        private val map = entries.associateBy { it.condition }
        infix fun from(value: Char) = map[value]
    }
}

data class ConditionRecord(val position: Int, val condition: SpringCondition)

fun String.parse(): Pair<List<ConditionRecord>, List<Int>> {
    val (conditions, numbers) = this.split(' ')

    return conditions.mapIndexed { x, c -> ConditionRecord(x, (SpringCondition from c)!!) } to numbers.split(',').map { it.toInt() }
}

class Day12 {

    @Test
    fun `given a string when parsed then a list of ConditionRecords is returned`() {

        val parsed = "???.### 1,1,3".parse()
        val expected = listOf(
                ConditionRecord(0, SpringCondition.UNKNOWN),
                ConditionRecord(1, SpringCondition.UNKNOWN),
                ConditionRecord(2, SpringCondition.UNKNOWN),
                ConditionRecord(3, SpringCondition.OPERATIONAL),
                ConditionRecord(4, SpringCondition.BROKEN),
                ConditionRecord(5, SpringCondition.BROKEN),
                ConditionRecord(6, SpringCondition.BROKEN))
        assertEquals(Pair(expected, listOf(1, 1, 3)), parsed)
    }
}