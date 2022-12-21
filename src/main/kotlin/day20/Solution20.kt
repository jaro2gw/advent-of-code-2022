package day20

import Input
import Presenter
import Solution
import day20.chain.Chain

fun main() = Presenter.present(Solution20)

object Solution20 : Solution {
    override fun part1(input: Input): String {
        val nums = input.lines().map { it.toInt() }
        return Chain().decrypt(nums).toString()
    }

    override fun part2(input: Input): String {
        val nums = input.lines().map { it.toInt() }
        return Chain().decrypt(nums, key = 811589153, rounds = 10).toString()
    }
}
