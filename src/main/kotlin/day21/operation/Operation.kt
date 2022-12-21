package day21.operation

private typealias Op = (Long, Long) -> Long

enum class Operation(private val op: Op) : Op by op {
    PLUS(Long::plus),
    MINUS(Long::minus),
    TIMES(Long::times),
    DIV(Long::div);

    companion object {
        fun fromChar(char: Char) = when (char) {
            '+'  -> PLUS
            '-'  -> MINUS
            '*'  -> TIMES
            '/'  -> DIV
            else -> throw IllegalArgumentException("Cannot convert char '$char' to an operation")
        }
    }
}
