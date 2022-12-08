package day05.crate.mover

import java.util.Stack

class CrateMover9001(size: Int) : CrateMoverBase(size) {
    override fun perform(operation: CrateMoverOperation) {
        val (count, source, target) = operation
        val crates = Stack<Char>()
        repeat(count) {
            val crate = stacks[source].pop()
            crates.push(crate)
        }
        repeat(count) {
            val crate = crates.pop()
            stacks[target].push(crate)
        }
    }
}
