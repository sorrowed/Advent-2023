package advent

import kotlin.test.Test
import kotlin.test.assertEquals
import advent.day5.MapLine
import kotlin.math.max
import kotlin.math.min

class Day5 {

    private val seedToSoilMap = listOf(
            MapLine.parse("50 98 2"),
            MapLine.parse("52 50 48"),
    )

    private val soilToFertilizerMap =
            listOf("0 15 37", "37 52 2", "39 0 15").map { MapLine.parse(it) }


    private val fertilizerToWaterMap =
            listOf("49 53 8", "0 11 42", "42 0 7", "57 7 4").map { MapLine.parse(it) }


    private val waterToLightMap =
            listOf("88 18 7", "18 25 70").map { MapLine.parse(it) }


    private val lightToTemperatureMap =
            listOf("45 77 23", "81 45 19", "68 64 13").map { MapLine.parse(it) }


    private val temperatureToHumidityMap =
            listOf("0 69 1", "1 0 69").map { MapLine.parse(it) }


    private val humidityToLocationMap =
            listOf("60 56 37", "56 93 4").map { MapLine.parse(it) }

    private fun seedToSoil(seed: Long) = advent.day5.find(seedToSoilMap, seed)

    private fun seedToLocation(seed: Long) =
            advent.day5.find(humidityToLocationMap,
                    advent.day5.find(temperatureToHumidityMap,
                            advent.day5.find(lightToTemperatureMap,
                                    advent.day5.find(waterToLightMap,
                                            advent.day5.find(fertilizerToWaterMap,
                                                    advent.day5.find(soilToFertilizerMap,
                                                            advent.day5.find(seedToSoilMap, seed)))))))

    @Test
    fun part1() {

        assertEquals(seedToSoil(79L), 81L)
        assertEquals(seedToSoil(14L), 14L)
        assertEquals(seedToSoil(55L), 57L)
        assertEquals(seedToSoil(13L), 13L)

        assertEquals(seedToLocation(79L), 82L)
        assertEquals(seedToLocation(14L), 43L)
        assertEquals(seedToLocation(55L), 86L)
        assertEquals(seedToLocation(13L), 35L)
    }

    data class Range(val begin: Long, val length: Long) {
        val end = begin + length

        fun overlap(that: Range): Range? {
            return if (begin <= that.end && end > that.begin) {
                val b = max(begin, that.begin)
                val e = min(end, that.end)

                Range(b, e - b)
            } else {
                null
            }
        }
    }

    @Test
    fun part2() {
        var lowest = Long.MAX_VALUE
        for (seed in 79L..<79L + 14) {
            lowest = minOf(lowest, seedToLocation(seed))
        }
        for (seed in 55L..<55L + 13) {
            lowest = minOf(lowest, seedToLocation(seed))
        }
        assertEquals(46, lowest)

        var seedRanges = listOf(Range(79, 14), Range(55, 13))

        var overlap1 = seedRanges[0].overlap(Range(seedToSoilMap[0].sourceBegin, seedToSoilMap[0].length))
        assertEquals(null, overlap1)

        var overlap2 = seedRanges[0].overlap(Range(seedToSoilMap[1].sourceBegin, seedToSoilMap[1].length))
        assertEquals(Range(79, 14), overlap2)
    }
}
