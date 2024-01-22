package advent.support

typealias NeighborFunction<T> = (node: T) -> List<T>

typealias EndFunction<T> = (node: T) -> Boolean
typealias CostFunction<T> = (from: T, to: T) -> Int
typealias HeuristicFunction<T> = (current: T) -> Int

typealias PastAndCost<T> = Pair<List<T>, Int>

class Pathfinding {


    companion object {
        fun <T> path(current: T, visited: Map<T, T>): List<T> {
            val path = mutableListOf(current)
            var from = current
            while (visited.containsKey(from)) {
                from = visited.getValue(from)
                path.add(0, from)
            }
            return path.toList()
        }

        fun <T> astar(start: T, isTarget: EndFunction<T>, neighbors: NeighborFunction<T>, moveCost: CostFunction<T>, heuristic: HeuristicFunction<T>): PastAndCost<T> {

            val open = mutableSetOf(start)
            val closed = mutableSetOf<T>()

            val costFromStart = mutableMapOf(start to 0)

            val totalCost = mutableMapOf(start to heuristic(start))

            val visited = mutableMapOf<T, T>()

            while (open.isNotEmpty()) {

                val current = open.minBy { totalCost.getValue(it) }
                if (isTarget(current)) {
                    val path = path(current, visited)
                    return Pair(path, totalCost[current]!!)
                } else {
                    open.remove(current)
                    closed.add(current)

                    neighbors(current)
                            .filterNot { closed.contains(it) }
                            .forEach { neighbor ->
                                val costOfMove = costFromStart[current]!! + moveCost(current, neighbor)

                                if (costOfMove < costFromStart.getOrDefault(neighbor, Int.MAX_VALUE)) {
                                    if (!open.contains(neighbor)) {
                                        open.add(neighbor)
                                    }

                                    visited[neighbor] = current
                                    costFromStart[neighbor] = costOfMove
                                    totalCost[neighbor] = costOfMove + heuristic(neighbor)
                                }
                            }
                }
            }
            throw Exception("No path found from $start")
        }
    }
}

