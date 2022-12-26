package day22

import Input
import SolutionTest

class Solution22Test : SolutionTest() {
    override val solution = Solution22(
        transitions = Input(
            clazz = Solution22Test::class.java,
            file = "example-transitions.txt"
        )
    )
    override val expected1 = "6032"
    override val expected2 = "5031"
}
