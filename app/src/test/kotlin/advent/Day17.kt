package advent

import advent.day17.Vertex
import advent.day17.City
import advent.support.Direction
import advent.support.Position
import kotlin.test.Test
import kotlin.test.assertEquals

class Day17 {
    val input = listOf(
            "2413432311323",
            "3215453535623",
            "3255245654254",
            "3446585845452",
            "4546657867536",
            "1438598798454",
            "4457876987766",
            "3637877979653",
            "4654967986887",
            "4564679986453",
            "1224686865563",
            "2546548887735",
            "4322674655533")


    private fun neighbors(city:City, vertex: Vertex):List<Vertex> {
        return Direction.entries
                .filter { it != Direction.UNKNOWN && (vertex.direction == Direction.UNKNOWN || it != vertex.direction.opposite()) }
                .map { vertex.move(it) }
                .filter { v -> v.moved <= 3 && v.position.within(city.limits.first, city.limits.second) }
    }

    @Test
    fun `given vertex when moved then target position is correct`() {

        var current = Vertex(Position(0,0), Direction.UNKNOWN,0)
        current = current.move(Direction.LEFT)
        assertEquals(Vertex(Position(-1,0),Direction.LEFT,1),current)
        current = current.move(Direction.RIGHT)
        assertEquals(Vertex(Position(0,0),Direction.RIGHT,1),current)
        current = current.move(Direction.DOWN)
        assertEquals(Vertex(Position(0,1),Direction.DOWN,1),current)
        current = current.move(Direction.UP)
        assertEquals(Vertex(Position(0,0),Direction.UP,1),current)
        current = current.move(Direction.DOWN)
        assertEquals(Vertex(Position(0,1),Direction.DOWN,1),current)
        current = current.move(Direction.DOWN)
        assertEquals(Vertex(Position(0,2),Direction.DOWN,2),current)
        current = current.move(Direction.DOWN)
        assertEquals(Vertex(Position(0,3),Direction.DOWN,3),current)
        current = current.move(Direction.UP)
        assertEquals(Vertex(Position(0,2),Direction.UP,1),current)
    }

    @Test
    fun `given vertex when asked for neighbors then result correct 1`() {

        val city = City.parse(input)
        val current = neighbors(city,Vertex(Position(6,6),Direction.RIGHT,1))
        assertEquals(
            listOf(
                Vertex(Position(6,5), Direction.UP,1),
                Vertex(Position(6,7),Direction.DOWN,1),
                Vertex(Position(7,6),Direction.RIGHT,2),
            ),
            current)
    }

    @Test
    fun `given vertex when asked for neighbors then result correct 2`() {

        val city = City.parse(input)

        val current = neighbors(city, Vertex(Position(6,6),Direction.RIGHT,3))
        assertEquals(
            listOf(
                Vertex(Position(6,5),Direction.UP,1),
                Vertex(Position(6,7),Direction.DOWN,1),
                ),
            current)
    }

    @Test
    fun `given input when parsed then path through city is correct`() {

        val path = City.parse(input).normalPath().also { print(it.first) }

        assertEquals(102, path.second)
    }

    @Test
    fun `given input when parsed then ultra path through city is correct`() {

        val city =City.parse(input)
        val path = city.ultraPath()

        city.print(path.first)

        assertEquals(94, path.second)
    }

    @Test
    fun `given more input when parsed then ultra path through city is correct`() {
        val input = listOf(
            "111111111111",
            "999999999991",
            "999999999991",
            "999999999991",
            "999999999991"
        )
        val city = City.parse(input)
        val path = city.ultraPath()

        city.print(path.first)

        assertEquals(71, path.second)
    }
}