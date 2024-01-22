package advent.day10

import advent.support.Position
import advent.support.Pathfinding
import advent.support.inside
import java.io.File

enum class Pipe(val type: Char) {
    NORTH_SOUTH('|'),
    EAST_WEST('-'),
    NORTH_EAST('L'),
    NORTH_WEST('J'),
    SOUTH_WEST('7'),
    SOUTH_EAST('F'),
    GROUND('.'),
    START('S');

    companion object {
        private val map = entries.associateBy { it.type }
        infix fun from(value: Char) = map[value]
    }

    fun isTraversable() = this != GROUND
}

class Maze(val tiles: Map<Position, Pair<Position, Pipe>>) {

    fun neighbors(position: Position): List<Position> {

        val (current, pipe) = tiles[position]!!

        val n = when (pipe) {
            Pipe.EAST_WEST -> listOf(Position(current.x - 1, current.y), Position(current.x + 1, current.y))
            Pipe.NORTH_SOUTH -> listOf(Position(current.x, current.y - 1), Position(current.x, current.y + 1))
            Pipe.NORTH_EAST -> listOf(Position(current.x, current.y - 1), Position(current.x + 1, current.y))
            Pipe.NORTH_WEST -> listOf(Position(current.x, current.y - 1), Position(current.x - 1, current.y))
            Pipe.SOUTH_WEST -> listOf(Position(current.x, current.y + 1), Position(current.x - 1, current.y))
            Pipe.SOUTH_EAST -> listOf(Position(current.x, current.y + 1), Position(current.x + 1, current.y))
            else -> throw Exception()
        }

        return n.filter { tiles.contains(it) && tiles[it]!!.second.isTraversable() }
    }

    companion object {
        fun parse(lines: List<String>): Maze {

            val tiles = lines
                    .flatMapIndexed { y: Int, line: String ->
                        line.mapIndexed { x, c -> Position(x, y) to (Pipe from c)!! }
                    }.associateBy { it.first }

            return Maze(tiles)
        }
    }
}

class Day10 {
    private val original = Maze.parse(File("app/src/main/kotlin/advent/input/day10.txt").readLines())

    private fun pathAndStart(): Pair<Position, List<Position>> {
        val startPosition = original.tiles.entries.first { it.value.second == Pipe.START }.key

        val tiles = original.tiles.toMutableMap()
        tiles.replace(startPosition, Pair(startPosition, Pipe.GROUND))

        val maze = Maze(tiles)

        // Start end end are to the south and to the east of startPosition
        val start = Position(startPosition.x, startPosition.y + 1)
        val end = Position(startPosition.x + 1, startPosition.y)

        val path = Pathfinding.astar(
                start,
                { node -> node == end },
                { node -> maze.neighbors(node) },
                { from, to -> from.manhattan(to) },
                { from -> from.manhattan(end) })

        return Pair(startPosition, path.first)
    }

    fun part1() {

        val (_, path) = pathAndStart()

        println("Day 10 part 1 : ${(path.size + 1) / 2}")
    }

    fun part2() {

        val (start, path) = pathAndStart()

        // The loop is a closed polygon if we also add the start to the path which we removed
        val polygon = path.toMutableList().also { it.add(start) }

        // Count tiles that don't belong to the polygon and are inside it
        val tiles = original.tiles.filter { !polygon.contains(it.value.first) && inside(it.value.first, polygon) }

        println("Day 10 part 2 : ${tiles.size}")
    }
}

fun run() {
    Day10().part1()
    Day10().part2()
}

