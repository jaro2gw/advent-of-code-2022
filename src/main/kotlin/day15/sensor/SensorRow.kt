package day15.sensor

import utils.Coords
import kotlin.math.abs

class SensorRow(
    private val row: Int
) {
    private val ranges = Ranges()
    private val obstacles = mutableSetOf<Int>()

    private fun obstacle(coords: Coords) {
        if (coords.row == row) obstacles += coords.col
    }

    fun add(sensor: Coords, beacon: Coords) {
        obstacle(sensor)
        obstacle(beacon)

        val distance = Coords.manhattanDistance(sensor, beacon)
        val dr = abs(sensor.row - row)
        if (dr >= distance) return

        val dc = distance - dr

        val lower = sensor.col - dc
        val upper = sensor.col + dc

        ranges += Range(lower, upper)
    }

    fun size(): Int = ranges.size() - obstacles.count { it in ranges }
}
