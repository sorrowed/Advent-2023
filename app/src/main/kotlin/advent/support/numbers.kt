package advent.support

import kotlin.math.abs

tailrec fun gcd(a: Long, b: Long): Long {
    if (b == 0L) {
        return a
    }
    return gcd(b, a % b)
}

fun lcm(a: Long, b: Long): Long {
    return abs(a * b) / gcd(a, b)
}

fun lcm(items: Iterable<Long>): Long {
    return items.drop(1).fold(items.first()) { total, current -> lcm(total, current) }
}