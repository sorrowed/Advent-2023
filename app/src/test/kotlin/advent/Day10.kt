package advent

import advent.day10.Maze
import advent.day10.Pipe
import advent.support.Position
import advent.support.Pathfinding

import kotlin.test.*

class Day10 {

    private val input = listOf(
            "7-F7-",
            ".FJ|7",
            ".JLL7", // "SJLL7",
            "|F--J",
            "LJ.LJ")

    @Test
    fun part1() {
        val maze = Maze.parse(input)
        assertEquals(25, maze.tiles.count())
        //assertEquals(Pipe.START, maze.tiles[Position(0, 2)]!!.second)
        assertEquals(Pipe.SOUTH_WEST, maze.tiles[Position(4, 2)]!!.second)

        val from = maze.tiles[Position(1, 2)]!!.first
        val target = maze.tiles[Position(0, 3)]!!.first

        val path = Pathfinding.astar(
                from,
                { node -> node == target },
                { node -> maze.neighbors(node) },
                { from, to -> from.manhattan(to) },
                { from -> from.manhattan(target) })

        assertEquals(8, (path.first.size + 1) / 2)
    }
}

