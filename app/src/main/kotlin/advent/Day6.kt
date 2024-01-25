package advent.day6

const val SPEED_PER_MS = 1

data class Race(val time: Long, val distance: Long) {

    fun distance(pressed: Long): Long {
        val speed = pressed * SPEED_PER_MS

        val remainingTime = time - pressed

        return remainingTime * speed
    }
}

class Day6 {
    fun part1() {
        val input = listOf(49, 87, 78, 95).zip(listOf(356, 1378, 1502, 1882)).map { Race(it.first.toLong(), it.second.toLong()) }

        val total = input.fold(1) { total, race -> (0..race.time).map { race.distance(it) }.count { it > race.distance } * total }

        println("Day 6 Part 1 : $total")
    }

    fun part2() {
        val race = Race(49877895, 356137815021882)
        val total = (0..race.time).map { race.distance(it) }.count { it > race.distance }
        println("Day 6 Part 2 : $total")
    }
}

fun run() {
    Day6().part1()
    Day6().part2()
}
