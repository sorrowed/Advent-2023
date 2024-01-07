package advent.support

fun Pair<String, String>.countDifferences(): Int {
    return first.zip(second).count { chars -> chars.first != chars.second }
}
