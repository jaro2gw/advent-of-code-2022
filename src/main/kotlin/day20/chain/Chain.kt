package day20.chain

class Chain {
    class Node(
        val value: Long,
    ) {
        companion object {
            fun swapWithNext(node: Node) {
                val n0 = node.prev
                val n1 = node
                val n2 = node.next
                val n3 = node.next.next

                n0.next = n2

                n2.prev = n0
                n2.next = n1

                n1.prev = n2
                n1.next = n3

                n3.prev = n1
            }
        }

        lateinit var prev: Node
        lateinit var next: Node

        fun move(times: Int) {
            if (times > 0) repeat(times) { swapWithNext(this) }
            else if (times < 0) repeat(-times) { swapWithNext(prev) }
        }

        fun next(nums: Int): Node =
            if (nums == 0) this
            else next.next(nums - 1)

        override fun toString(): String = generateSequence(this) { node -> node.next.takeUnless { it === this } }
            .joinToString(
                prefix = "-> ",
                postfix = " <-",
                separator = " <-> "
            ) { it.value.toString() }
    }

    fun decrypt(nums: List<Int>, key: Long = 1, rounds: Int = 1): Long {
        val first = Node(nums[0] * key)
        val nodes = mutableListOf(first)

        var temp: Node? = null
        if (nums[0] == 0) temp = first

        val last = nums.drop(1).fold(first) { prev, num ->
            val next = Node(num * key)
            nodes += next
            if (num == 0) temp = next
            next.prev = prev
            prev.next = next
            return@fold next
        }
        first.prev = last
        last.next = first

        val zero = checkNotNull(temp)

        repeat(rounds) {
            nodes.forEach { node ->
                val times = node.value % (nums.size - 1)
                node.move(times.toInt())
            }
        }

        val move = 1000 % nums.size
        return generateSequence(zero) { it.next(move) }
            .drop(1)
            .take(3)
            .sumOf { it.value }
    }
}
