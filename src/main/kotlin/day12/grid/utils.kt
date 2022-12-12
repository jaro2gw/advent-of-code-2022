package day12.grid

operator fun Array<IntArray>.get(coords: Coords): Int = this[coords.row][coords.col]

operator fun Array<IntArray>.set(coords: Coords, value: Int) {
    this[coords.row][coords.col] = value
}

operator fun Array<IntArray>.contains(coords: Coords): Boolean {
    return coords.row in this.indices && coords.col in this[coords.row].indices
}
