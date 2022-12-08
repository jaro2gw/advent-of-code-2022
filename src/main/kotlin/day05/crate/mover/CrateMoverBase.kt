package day05.crate.mover

import java.util.Stack

sealed class CrateMoverBase(final override val size: Int) : CrateMover {
    protected val stacks = Array(size) {
        Stack<Char>()
    }

    override fun push(index: Int, crate: Char) {
        stacks[index].push(crate)
    }

    override fun peek(index: Int): Char = stacks[index].peek()
}
