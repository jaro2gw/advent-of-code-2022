package day24.blizzard

import utils.Coords
import utils.contains
import utils.get
import utils.set

class Blizzard(
    val rows: Int,
    val cols: Int,
) {
    private fun propagate(vortexes: List<Vortex>): List<Vortex> = vortexes.map { (coords, direction) ->
        var (row, col) = coords + direction.vector

        if (row == 0) row = rows - 2
        else if (row == rows - 1) row = 1

        if (col == 0) col = cols - 2
        else if (col == cols - 1) col = 1

        Vortex(
            coords = Coords(row, col),
            direction
        )
    }

    private fun valley(vortexes: List<Vortex>): Array<BooleanArray> {
        val valley = Array(rows) { BooleanArray(cols) { true } }

        // sides
        for (row in 0 until rows) {
            valley[row][0] = false
            valley[row][cols - 1] = false
        }

        // top row
        for (col in 2 until cols - 1) {
            valley[0][col] = false
        }

        // bottom row
        for (col in 1 until cols - 2) {
            valley[rows - 1][col] = false
        }

        // vortexes
        for ((coords, _) in vortexes) {
            valley[coords] = false
        }

        return valley
    }

    fun quickestPath(initial: List<Vortex>, start: Coords, end: Coords): Pair<List<Vortex>, Int> {
        var vortexes = initial
        var steps = 0

        var options = setOf(start)
        while (true) {
            if (options.isEmpty()) throw IllegalStateException("Could not reach the end.")
            if (end in options) return vortexes to steps

            steps += 1
            vortexes = propagate(vortexes)

            val valley = valley(vortexes)
            options = options
                .flatMap { coords ->
                    Coords.immediateNeighbours(coords)
                        .plus(coords)
                        .filter { it in valley && valley[it] }
                }
                .toSet()
        }
    }
}
