package day05

class CrateMover9000(size: Int) : CrateMoverBase(size) {
    override fun perform(operation: CrateMoverOperation) {
        val (count, source, target) = operation
        repeat(count) {
            val crate = stacks[source].pop()
            stacks[target].push(crate)
        }
    }
}
