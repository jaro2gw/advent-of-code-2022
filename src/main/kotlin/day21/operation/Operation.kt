package day21.operation

private typealias Op = (Double, Double) -> Double

enum class Operation(private val op: Op) : Op by op {
    PLUS(Double::plus),
    MINUS(Double::minus),
    TIMES(Double::times),
    DIV(Double::div);

    companion object {
        fun fromChar(char: Char) = when (char) {
            '+'  -> PLUS
            '-'  -> MINUS
            '*'  -> TIMES
            '/'  -> DIV
            else -> throw IllegalArgumentException("Cannot convert char '$char' to an operation")
        }

//        fun inverse(operation: Operation) = when (operation) {
//            PLUS  -> MINUS
//            MINUS -> PLUS
//            TIMES -> DIV
//            DIV   -> TIMES
//        }
    }
}
