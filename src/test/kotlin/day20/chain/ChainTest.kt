package day20.chain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ChainTest {
    @Test
    fun `should swap neighbouring nodes correctly`() {
        val n0 = Chain.Node(0)
        val n1 = Chain.Node(1)
        val n2 = Chain.Node(2)
        val n3 = Chain.Node(3)

        n0.next = n1
        n1.next = n2
        n2.next = n3
        n3.next = n0

        n0.prev = n3
        n1.prev = n0
        n2.prev = n1
        n3.prev = n2

        // current chain:
        // -> n0 <-> n1 <-> n2 <-> n3 <-

        Chain.Node.swapWithNext(n1)

        // expected chain:
        // -> n0 <-> n2 <-> n1 <-> n3 <-

        assertEquals(n2, n0.next)
        assertEquals(n3, n0.prev)

        assertEquals(n1, n2.next)
        assertEquals(n0, n2.prev)

        assertEquals(n3, n1.next)
        assertEquals(n2, n1.prev)

        assertEquals(n0, n3.next)
        assertEquals(n1, n3.prev)
    }
}
