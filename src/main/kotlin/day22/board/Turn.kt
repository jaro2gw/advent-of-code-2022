package day22.board

enum class Turn(val char: Char) {
    LEFT('L'),
    RIGHT('R');

    companion object {
        fun fromChar(char: Char) = values().single { it.char == char }
    }

    override fun toString(): String = "$char"
}
