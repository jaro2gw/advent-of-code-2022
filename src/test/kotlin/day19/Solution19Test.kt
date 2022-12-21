package day19;

//import Input
//import Presenter
import SolutionTest
import org.junit.jupiter.api.Disabled

//fun main() = Presenter.present(Solution19, Input(Solution19Test::class.java, "example.txt"))

class Solution19Test : SolutionTest() {
    override val solution = Solution19
    override val expected1 = "33"
    override val expected2 = "3472"

    @Disabled(
        """
        For some reason, part 2 test runs for a longer time with the example input than with the actual input.
        On my machine the second part with the example input runs in ~34s. With actual input it runs in ~10s.
        If you want to test the solution manually, you can uncomment and run the `main` function above (remember to uncomment the imports too).
        """
    )
    override fun part2() = super.part2()
}
