package day12

import Input
import Presenter
import Solution
import day12.grid.Grid
import day12.grid.PathFinder
import utils.Coords

fun main() = Presenter.present(Solution12)

object Solution12 : Solution {
    private fun grid(input: Input): Grid {
        var start: Coords? = null
        var end: Coords? = null

        fun elevation(char: Char, row: Int, col: Int) = when (char) {
            'S' -> {
                start = Coords(row, col)
                0
            }

            'E' -> {
                end = Coords(row, col)
                26
            }

            in 'a'..'z' -> char - 'a'

            else -> throw IllegalArgumentException("Could not determine the elevation of tile '$char'")
        }

        val elevation = input.lines()
            .mapIndexed { row, line ->
                line.mapIndexed { col, c -> elevation(c, row, col) }
                    .toIntArray()
            }
            .toTypedArray()

        checkNotNull(start) { "Could not find the starting position in the grid" }
        checkNotNull(end) { "Could not find the ending position in the grid" }

        return Grid(elevation, start!!, end!!)
    }

    override fun part1(input: Input): String {
        val grid = grid(input)
        return PathFinder().shortestUp(grid).toString()
    }

    override fun part2(input: Input): String {
        val grid = grid(input)
        return PathFinder().shortestDown(grid).toString()
    }
}
