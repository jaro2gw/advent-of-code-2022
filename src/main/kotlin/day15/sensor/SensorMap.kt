package day15.sensor

import utils.Coords
import kotlin.math.abs

class SensorMap(
    private val lower: Int,
    private val upper: Int,
) {
    private val ranges: MutableMap<Int, Ranges> = HashMap(upper - lower + 1)

    init {
        for (row in lower..upper) {
            ranges[row] = Ranges()
        }
    }

    private fun distance(coords1: Coords, coords2: Coords): Int =
        abs(coords1.row - coords2.row) + abs(coords1.col - coords2.col)

    fun add(sensor: Coords, beacon: Coords) {
        val distance = distance(sensor, beacon)

        var diff = 0
        var row = sensor.row - distance

        fun updateRanges(update: (Int) -> Int) {
            val lower = sensor.col - diff
            val upper = sensor.col + diff

            ranges[row]?.apply {
                add(range = Range(lower, upper))
                if (continuous(this@SensorMap.lower, this@SensorMap.upper)) {
                    ranges -= row
                }
            }

            row += 1
            diff = update(diff)
        }

        repeat(distance) {
            updateRanges { it + 1 }
        }

        repeat(distance + 1) {
            updateRanges { it - 1 }
        }
    }

    fun missingBeacon(): Coords {
        val (row, range) = ranges.entries.single()
        val col = range.missingValue()
        return Coords(row, col)
    }
}
