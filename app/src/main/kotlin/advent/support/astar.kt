package advent.support

import kotlin.math.abs

data class Position(val x: Int, val y: Int) {
    fun distanceTo(to: Position): Int {
        return abs(to.x - x) + abs(to.y - y)
    }
}

fun aStar(start: Position, target: Position, neightbors: (from: Position) -> List<Position>, moveCost: (from: Position, to: Position) -> Int, heuristic: (from: Position, to: Position) -> Int): Pair<List<Position>, Int> {

    fun generatePath(current: Position, visited: Map<Position, Position>): List<Position> {
        val path = mutableListOf(current)
        var from = current
        while (visited.containsKey(from)) {
            from = visited.getValue(from)
            path.add(0, from)
        }
        return path.toList()
    }


    val open = mutableSetOf(start)
    val closed = mutableSetOf<Position>()

    val costFromStart = mutableMapOf(start to 0)

    val totalCost = mutableMapOf(start to heuristic(start, target))

    val visited = mutableMapOf<Position, Position>()

    while (open.size > 0) {

        val current = open.minBy { totalCost.getValue(it) }
        if (current == target) {
            val path = generatePath(current, visited)
            return Pair(path, totalCost[target]!!)
        } else {
            open.remove(current)
            closed.add(current)

            neightbors(current)
                    .filterNot { closed.contains(it) }
                    .forEach { neighbor ->
                        val costOfMove = costFromStart[current]!! + moveCost(current, neighbor)

                        if (costOfMove < costFromStart.getOrDefault(neighbor, Int.MAX_VALUE)) {
                            if (!open.contains(neighbor)) {
                                open.add(neighbor)
                            }

                            visited[neighbor] = current
                            costFromStart[neighbor] = costOfMove
                            totalCost[neighbor] = costOfMove + heuristic(neighbor, target)
                        }
                    }
        }
    }
    throw Exception()
}
