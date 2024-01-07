package advent.day12

fun groupsAndTemplate(line: String): Pair<String, List<Int>> {
    return line.split(" ").run {
        first() to last().split(",").map { it.toInt() }
    }
}

fun arrangements(records: List<String>, template: List<Int>): Int {
    return 0
}


class Day12 {
    fun part1() {

        println("Day 12 part 1 :")
    }

    fun part2() {
        println("Day 12 part 2 :")
    }
}

fun run() {
    Day12().part1()
    Day12().part2()
}