package day10.cpu

class CRT {
    private val board = Array(6) {
        BooleanArray(40)
    }

    operator fun set(row: Int, col: Int, lit: Boolean) {
        board[row][col] = lit
    }

    override fun toString(): String = board.joinToString(separator = System.lineSeparator()) { pixels ->
        pixels.joinToString(separator = "") { lit ->
            if (lit) "#"
            else "."
        }
    }
}
