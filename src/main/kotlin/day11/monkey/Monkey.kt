package day11.monkey

import java.util.LinkedList

class Monkey(
    items: List<Long>,
    private val operation: (Long) -> Long,
    private val prime: Long,
    private val accept: (Long) -> Unit,
    private val reject: (Long) -> Unit,
    private val divide: Long,
) {
    companion object {
        private var PRIMES = 1L
    }

    init {
        if (PRIMES % prime != 0L) PRIMES *= prime
    }

    var inspections: Long = 0L
        private set

    private val items = LinkedList(items)

    fun catch(item: Long) {
        items += item
    }

    fun turn() {
        inspections += items.size
        while (items.isNotEmpty()) {
            val item = items.pollFirst()
            val worry = operation(item % PRIMES) / divide
            if (worry % prime == 0L) accept(worry)
            else reject(worry)
        }
    }
}
