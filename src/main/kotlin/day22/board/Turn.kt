package day22.board

import utils.Direction

enum class Turn(val char: Char, private val diff: Int) : (Direction) -> Direction {
    LEFT('L', -1),
    RIGHT('R', +1);

    companion object {
        fun fromChar(char: Char) = values().single { it.char == char }
    }

    override fun invoke(direction: Direction): Direction {
        val index = (direction.ordinal + diff + 4) % 4
        return Direction.values()[index]
    }

    override fun toString(): String = "$char"
}
