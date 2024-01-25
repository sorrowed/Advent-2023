package advent.support

import kotlin.math.absoluteValue

fun bounds(points: Iterable<Position>): Pair<Position, Position> {
    return Pair(
            Position(points.minBy { it.x }.x, points.minBy { it.y }.y),
            Position(points.maxBy { it.x }.x, points.maxBy { it.y }.y))
}

fun perimeter(points: Iterable<Position>): Double {
    val result = points.drop(1)
            .fold(0.0 to points.first()) { (length, previous), position -> length + previous.distance(position) to position }

    return result.first + result.second.distance(points.first())
}

/**
 * Shoelace algorithm : https://en.wikipedia.org/wiki/Shoelace_formula
 */
fun area(points: Iterable<Position>): Long {

    fun determinant(from: Position, to: Position): Long {
        return (from.x.toLong() * to.y.toLong()) - (from.y.toLong() * to.x.toLong())
    }

    val result = points.drop(1)
            .fold(0L to points.first()) { (area, previous), position -> area + determinant(previous, position) to position }

    return ((result.first + determinant(result.second, points.first())) / 2).absoluteValue
}

/**
 * Return the number of integer points inside the a polygon with given area and perimiter
 * Picks theorem : https://en.wikipedia.org/wiki/Pick's_theorem
 */
fun pointsInside(area: Long, perimeter: Long): Long = area - perimeter / 2 + 1

/*!
* See https://web.archive.org/web/20110314030147/http://paulbourke.net/geometry/insidepoly/
*/
fun Position.inside(polygon: List<Position>): Boolean {
    var result = false


    var i = 0
    var j = polygon.size - 1
    while (i < polygon.size) {
        if (((polygon[i].y <= this.y && this.y < polygon[j].y) || (polygon[j].y <= this.y && this.y < polygon[i].y)) &&
                (this.x < (polygon[j].x - polygon[i].x) * (this.y - polygon[i].y) / (polygon[j].y - polygon[i].y) + polygon[i].x)) {
            result = result.not()
        }
        j = i++
    }

    return result
}