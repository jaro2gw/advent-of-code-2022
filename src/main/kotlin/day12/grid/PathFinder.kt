package day12.grid

import utils.Coords
import utils.get
import utils.set

class PathFinder {
    companion object {
        private val NEIGHBOURS = listOf(
            Coords(row = 0, col = -1), // left
            Coords(row = 0, col = +1), // right
            Coords(row = -1, col = 0), // up
            Coords(row = +1, col = 0), // down
        )
    }

    private fun neighbours(coords: Coords) = NEIGHBOURS.map { coords + it }

    private fun climb(
        coords: Coords,
        grid: Grid,
        distance: Array<IntArray>,
        predicate: (source: Int, target: Int) -> Boolean
    ) {
        val elev = grid[coords]
        val dist = distance[coords] + 1
        neighbours(coords)
            .filter { it in grid }
            .filter { distance[it] > dist }
            .filter { predicate(elev, grid[it]) }
            .onEach { distance[it] = dist }
            .forEach { climb(it, grid, distance, predicate) }
    }

    private fun prepareDistanceArray(grid: Grid) = Array(grid.rows) {
        IntArray(grid.cols) { Int.MAX_VALUE }
    }

    fun shortestUp(grid: Grid): Int {
        val distance = prepareDistanceArray(grid)

        distance[grid.start] = 0
        climb(grid.start, grid, distance) { source, target -> target <= source + 1 }

        return distance[grid.end]
    }

    fun shortestDown(grid: Grid): Int {
        val distance = prepareDistanceArray(grid)

        distance[grid.end] = 0
        climb(grid.end, grid, distance) { source, target -> target >= source - 1 }

        return distance
            .flatMapIndexed { row, ints ->
                ints.mapIndexed { col, dist -> Coords(row, col) to dist }
            }
            .filter { (coords, _) -> grid[coords] == 0 }
            .minOf { (_, dist) -> dist }
    }
}
