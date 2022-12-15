package day15;

import Input
import Presenter
import Solution
import day15.sensor.SensorMap
import day15.sensor.SensorRow
import utils.Coords
import utils.NUMBER_PATTERN
import kotlin.math.abs

fun main() = Presenter.present(solution = Solution15(row = 2_000_000, bound = 4_000_000))

class Solution15(
    private val row: Int,
    private val bound: Int
) : Solution {
    private val SENSOR_REGEX = Regex(
        "Sensor at x=$NUMBER_PATTERN, y=$NUMBER_PATTERN: closest beacon is at x=$NUMBER_PATTERN, y=$NUMBER_PATTERN"
    )

    private fun distance(coords1: Coords, coords2: Coords): Int =
        abs(coords1.row - coords2.row) + abs(coords1.col - coords2.col)

    private fun distance(coords: Coords, row: Int) = abs(coords.row - row)

    private fun coords(input: Input): List<Pair<Coords, Coords>> = input.lines()
        .asSequence()
        .mapNotNull { SENSOR_REGEX.find(it) }
        .map { it.groupValues }
        .map { it.drop(1) }
        .map { it.map(String::toInt) }
        .map { (xs, ys, xb, yb) ->
            Coords(row = ys, col = xs) to Coords(row = yb, col = xb)
        }
        .toList()

    override fun part1(input: Input): String {
        val row = SensorRow(row)

        coords(input).forEach { (sensor, beacon) ->
            row.add(sensor, beacon)
        }

        return row.size().toString()
    }

    override fun part2(input: Input): String {
        val map = SensorMap(0, bound)
        coords(input).forEach { (sensor, beacon) ->
            map.add(sensor, beacon)
        }
        val (row, col) = map.missingBeacon()
        return (col * 4_000_000L + row).toString()
    }
}
