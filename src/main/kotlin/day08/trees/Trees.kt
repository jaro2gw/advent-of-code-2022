package day08.trees

class Trees(private val trees: Array<IntArray>) {
    val rows = trees.size
    val cols = trees[0].size

    fun rows() = 0 until rows
    fun cols() = 0 until cols

    fun indices(): Sequence<Pair<Int, Int>> = sequence {
        for (row in rows()) {
            for (col in cols()) {
                val index = row to col
                yield(index)
            }
        }
    }

    init {
        for (row in rows()) {
            check(trees[row].size == cols)
        }
    }

    operator fun get(row: Int, col: Int): Int = trees[row][col]
}
