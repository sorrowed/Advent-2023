package advent.support

fun <T> List<T>.pairs(): List<Pair<T, T>> {
    return mapIndexed { index, left ->
        slice(index + 1..<size)
                .map { right -> Pair(left, right) }
    }.flatten()
}
