package advent.day17

import advent.support.Direction
import advent.support.Pathfinding
import advent.support.Position
import java.io.File

data class Vertex(val position: Position, val direction: Direction, val moved: Int) {

    fun move(dir: Direction): Vertex {
        return Vertex(position.move(dir), dir, if (dir == direction) {
            moved + 1
        } else {
            1
        })
    }
}

class City(val nodes: Map<Position, Long>) {

    val limits =
            Position(nodes.keys.minBy { it.x }.x, nodes.keys.minBy { it.y }.y) to
                    Position(nodes.keys.maxBy { it.x }.x, nodes.keys.maxBy { it.y }.y)


    private val cost = { _: Vertex, to: Vertex -> nodes[to.position]!! }
    private val heuristic = { vertex: Vertex -> vertex.position.manhattan(limits.second) }

    fun normalPath(): Pair<List<Position>, Long> {

        val neighbors = { vertex: Vertex ->
            when (vertex.moved) {
                0 -> {
                    listOf(vertex.move(Direction.UP),
                            vertex.move(Direction.LEFT),
                            vertex.move(Direction.DOWN),
                            vertex.move(Direction.RIGHT))
                }

                in 1..2 -> {
                    listOf(vertex.move(vertex.direction.left()),
                            vertex.move(vertex.direction),
                            vertex.move(vertex.direction.right()))
                }

                else -> {
                    listOf(vertex.move(vertex.direction.left()),
                            vertex.move(vertex.direction.right()))
                }
            }.filter { v -> v.position.within(limits.first, limits.second) }
        }

        val endFunction = { vertex: Vertex -> vertex.position == limits.second }

        val path = Pathfinding.astar(
                Vertex(limits.first, Direction.UNKNOWN, 0),
                endFunction,
                neighbors,
                cost,
                heuristic)

        return path.first.map { it.position } to path.second
    }

    fun ultraPath(): Pair<List<Position>, Long> {
        val neighbors = { vertex: Vertex ->
            when (vertex.moved) {
                0 -> {
                    listOf(vertex.move(Direction.UP),
                            vertex.move(Direction.LEFT),
                            vertex.move(Direction.DOWN),
                            vertex.move(Direction.RIGHT))
                }

                in 1..3 -> {
                    listOf(vertex.move(vertex.direction))
                }

                in 4..9 -> {
                    listOf(vertex.move(vertex.direction.left()),
                            vertex.move(vertex.direction),
                            vertex.move(vertex.direction.right()))
                }

                else -> {
                    listOf(vertex.move(vertex.direction.left()),
                            vertex.move(vertex.direction.right()))
                }
            }.filter { v -> v.position.within(limits.first, limits.second) }
        }
        val endFunction = { vertex: Vertex -> vertex.moved >= 4 && vertex.position == limits.second }

        val path = Pathfinding.astar(
                Vertex(limits.first, Direction.UNKNOWN, 0),
                endFunction,
                neighbors,
                cost,
                heuristic)

        return path.first.map { it.position } to path.second
    }

    fun print(path: List<Position>) {

        for (y in limits.first.y..limits.second.y) {
            for (x in limits.first.x..limits.second.x) {

                val p = Position(x, y)

                val c = if (path.contains(p)) {
                    '*'
                } else {
                    '0' + nodes[p]!!.toInt()
                }
                print(c)
            }
            println()
        }
    }

    companion object {
        fun parse(input: List<String>): City {
            return City(input.flatMapIndexed { y: Int, s: String ->
                s.mapIndexed { x, c -> Position(x.toLong(), y.toLong()) to c.digitToInt().toLong() }
            }.associate { it })
        }
    }
}

class Day17 {
    private val city = File("app/src/main/kotlin/advent/input/day17.txt").readLines().run { City.parse(this) }

    fun part1() {

        // These are sloooooooow
//        val path = city.normalPath()
//        println("Day 17 part 1 : ${path.second}")
        println("Day 17 part 1 : 866")
    }

    fun part2() {
        // These are sloooooooow
//        val path = city.ultraPath()
//        println("Day 17 part 2 : ${path.second}")
        println("Day 17 part 2 : 1010")
    }
}

fun run() {
    Day17().part1()
    Day17().part2()
}