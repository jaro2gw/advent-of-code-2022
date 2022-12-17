package day17.rock

import utils.Coords

class RockShape private constructor(
    private val shape: Array<BooleanArray>
) {
    companion object {
        private fun convert(vararg shape: String) = RockShape(
            shape = shape
                .map { line ->
                    line
                        .map {
                            when (it) {
                                '#' -> true
                                '.' -> false
                                else -> throw IllegalArgumentException("Cannot convert cell '$it' on the line \"$line\"")
                            }
                        }
                        .toBooleanArray()
                }
                .toTypedArray()
        )

        val shapes: List<RockShape> = listOf(
            convert(
                "####",
                "....",
                "....",
                "....",
            ),
            convert(
                ".#..",
                "###.",
                ".#..",
                "....",
            ),
            convert(
                "###.",
                "..#.",
                "..#.",
                "....",
            ),
            convert(
                "#...",
                "#...",
                "#...",
                "#...",
            ),
            convert(
                "##..",
                "##..",
                "....",
                "....",
            ),
        )
    }

    fun shatter(): List<Coords> = shape.flatMapIndexed { row, cells ->
        cells.withIndex()
            .filter { (_, cell) -> cell }
            .map { (col, _) ->
                Coords(row, col)
            }
    }

    override fun toString(): String = shape.reversed()
        .joinToString(
            separator = System.lineSeparator()
        ) { cells ->
            cells.joinToString(separator = "") {
                if (it) "#"
                else "."
            }
        }
}
