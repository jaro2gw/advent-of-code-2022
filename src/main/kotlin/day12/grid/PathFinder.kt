package day12.grid

class PathFinder {
    private fun climbUp(coords: Coords, elevation: Array<IntArray>, distance: Array<IntArray>) {
        val max = elevation[coords] + 1
        val dist = distance[coords] + 1
        coords.neighbours()
            .filter { it in elevation }
            .filter { distance[it] > dist }
            .filter { elevation[it] <= max }
            .onEach { distance[it] = dist }
            .forEach { climbUp(it, elevation, distance) }
    }

    private fun climbDown(coords: Coords, elevation: Array<IntArray>, distance: Array<IntArray>) {
        val min = elevation[coords] - 1
        val dist = distance[coords] + 1
        coords.neighbours()
            .filter { it in elevation }
            .filter { distance[it] > dist }
            .filter { elevation[it] >= min }
            .onEach { distance[it] = dist }
            .forEach { climbDown(it, elevation, distance) }
    }

    fun shortestUp(grid: Grid): Int {
        val distance = Array(grid.elevation.size) {
            IntArray(grid.elevation[it].size) { Int.MAX_VALUE }
        }

        distance[grid.start] = 0
        climbUp(grid.start, grid.elevation, distance)

        return distance[grid.end]
    }

    fun shortestDown(grid: Grid): Int {
        val distance = Array(grid.elevation.size) {
            IntArray(grid.elevation[it].size) { Int.MAX_VALUE }
        }

        distance[grid.end] = 0
        climbDown(grid.end, grid.elevation, distance)

        return distance
            .flatMapIndexed { row, ints ->
                ints.mapIndexed { col, dist -> Coords(row, col) to dist }
            }
            .filter { (coords, _) -> grid.elevation[coords] == 0 }
            .minOf { (_, dist) -> dist }
    }
}
