package advent.day5

data class MapLine(val destinationBegin: Long, val sourceBegin: Long, val length: Long) {

    private val soureEnd = sourceBegin + length

    fun matchesSource(sourceValue: Long) = sourceValue in sourceBegin..<soureEnd

    companion object {
        fun parse(line: String): MapLine {
            val tokens = line.split(' ')
            return MapLine(tokens[0].toLong(), tokens[1].toLong(), tokens[2].toLong())
        }
    }
}

fun find(map: List<MapLine>, sourceValue: Long): Long {
    val line = map.find { line -> line.matchesSource(sourceValue) }
    return if (line != null) {
        line.destinationBegin + sourceValue - line.sourceBegin
    } else {
        sourceValue
    }
}


class Day5 {
    private val seedToSoilMap = toMap(Input().seedToSoilInput)
    private val soilToFertilizerMap = toMap(Input().soilToFertilizerInput)
    private val fertilizerToWaterMap = toMap(Input().fertilizerToWaterInput)
    private val waterTolightMap = toMap(Input().waterToLightInput)
    private val lightToTemperatureMap = toMap(Input().lightToTemperatureInput)
    private val temperatureToHumidityMap = toMap(Input().temperatureToHumidityInput)
    private val humidityToLocationMap = toMap(Input().humidityToLocationInput)

    private fun seedToLocation(seed: Long) =
            find(humidityToLocationMap,
                    find(temperatureToHumidityMap,
                            find(lightToTemperatureMap,
                                    find(waterTolightMap,
                                            find(fertilizerToWaterMap,
                                                    find(soilToFertilizerMap,
                                                            find(seedToSoilMap, seed)))))))

    private val seeds = Input().seeds.fold(mutableListOf<Long>()) { list, line ->
        list.add(line.toLong())
        list
    }.toList()

    fun part1() {

        val locations = seeds.map { seedToLocation(it) }
        val lowest = locations.min()

        println("Day 5 Part 1 : $lowest")
    }

    fun part2() {
        println("Day 5 Part 1")
    }
}

fun run() {
    Day5().part1()
    Day5().part2()
}


