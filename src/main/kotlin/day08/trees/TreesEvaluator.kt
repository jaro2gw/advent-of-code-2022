package day08.trees

import kotlin.math.max

class TreesEvaluator {
    private fun checkVisibility(
        trees: Trees,
        visible: Array<BooleanArray>,
        xs: IntProgression,
        ys: IntProgression,
        coords: (Int, Int) -> Pair<Int, Int>
    ) {
        for (x in xs) {
            var max = -1
            for (y in ys) {
                val (row, col) = coords(x, y)
                val tree = trees[row, col]
                if (tree > max) visible[row][col] = true
                max = max(max, tree)
            }
        }
    }

    private fun checkVisibilityHorizontal(
        trees: Trees,
        visible: Array<BooleanArray>,
        rows: IntProgression,
        cols: IntProgression,
    ) = checkVisibility(
        trees = trees,
        visible = visible,
        xs = rows,
        ys = cols
    ) { r, c -> r to c }

    private fun checkVisibilityVertical(
        trees: Trees,
        visible: Array<BooleanArray>,
        rows: IntProgression,
        cols: IntProgression,
    ) = checkVisibility(
        trees = trees,
        visible = visible,
        xs = cols,
        ys = rows
    ) { c, r -> r to c }


    fun visibleTrees(trees: Trees): Int {
        val visible = Array(trees.rows) { BooleanArray(trees.cols) }
        val rows = trees.rows()
        val cols = trees.cols()

        checkVisibilityHorizontal(trees, visible, rows, cols)
        checkVisibilityHorizontal(trees, visible, rows, cols.reversed())

        checkVisibilityVertical(trees, visible, rows, cols)
        checkVisibilityVertical(trees, visible, rows.reversed(), cols)

        return visible.sumOf { booleans -> booleans.count { it } }
    }

    private fun countDistance(
        trees: Trees,
        x: Int,
        ys: IntProgression,
        spot: Int,
        coords: (Int, Int) -> Pair<Int, Int>
    ): Int {
        var count = 0
        for (y in ys) {
            count += 1
            val (row, col) = coords(x, y)
            val tree = trees[row, col]
            if (tree >= spot) break
        }
        return count
    }

    private fun countDistanceHorizontal(
        trees: Trees,
        row: Int,
        cols: IntProgression,
        spot: Int
    ): Int = countDistance(
        trees = trees,
        x = row,
        ys = cols,
        spot = spot
    ) { r, c -> r to c }

    private fun countDistanceVertical(
        trees: Trees,
        rows: IntProgression,
        col: Int,
        spot: Int
    ): Int = countDistance(
        trees = trees,
        x = col,
        ys = rows,
        spot = spot
    ) { c, r -> r to c }

    private fun countDistanceLeft(trees: Trees, row: Int, col: Int): Int = countDistanceHorizontal(
        trees = trees,
        row = row,
        cols = (0 until col).reversed(),
        spot = trees[row, col],
    )

    private fun countDistanceRight(trees: Trees, row: Int, col: Int): Int = countDistanceHorizontal(
        trees = trees,
        row = row,
        cols = (col + 1) until trees.cols,
        spot = trees[row, col],
    )

    private fun countDistanceUp(trees: Trees, row: Int, col: Int): Int = countDistanceVertical(
        trees = trees,
        rows = (0 until row).reversed(),
        col = col,
        spot = trees[row, col],
    )

    private fun countDistanceDown(trees: Trees, row: Int, col: Int): Int = countDistanceVertical(
        trees = trees,
        rows = (row + 1) until trees.rows,
        col = col,
        spot = trees[row, col],
    )

    private fun calculateScenicScore(trees: Trees, row: Int, col: Int): Int = countDistanceLeft(trees, row, col) *
            countDistanceRight(trees, row, col) *
            countDistanceUp(trees, row, col) *
            countDistanceDown(trees, row, col)

    fun scenicScore(trees: Trees): Int = trees.indices().maxOf { (row, col) ->
        calculateScenicScore(trees, row, col)
    }
}
