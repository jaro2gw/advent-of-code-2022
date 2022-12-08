package day05.crate.mover

interface CrateMover {
    val size: Int
    fun push(index: Int, crate: Char)
    fun perform(operation: CrateMoverOperation)
    fun peek(index: Int): Char
    fun peek(): String = buildString(size) {
        repeat(size) {
            append(peek(it))
        }
    }
}
