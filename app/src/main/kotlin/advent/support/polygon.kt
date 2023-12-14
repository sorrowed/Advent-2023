package advent.support

fun bounds(points: List<Position>): Pair<Position, Position> {
    return Pair(
            Position(points.minBy { it.x }.x, points.minBy { it.y }.y),
            Position(points.maxBy { it.x }.x, points.maxBy { it.y }.y))
}

/*!
* See https://web.archive.org/web/20110314030147/http://paulbourke.net/geometry/insidepoly/
*/
fun inside(point: Position, polygon: List<Position>): Boolean {
    var result = false


    var i = 0
    var j = polygon.size - 1
    while (i < polygon.size) {
        if (((polygon[i].y <= point.y && point.y < polygon[j].y) || (polygon[j].y <= point.y && point.y < polygon[i].y)) &&
                (point.x < (polygon[j].x - polygon[i].x) * (point.y - polygon[i].y) / (polygon[j].y - polygon[i].y) + polygon[i].x)) {
            result = result.not()
        }
        j = i++
    }

    return result;
}