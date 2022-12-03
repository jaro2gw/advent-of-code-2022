package day03

import Input
import Presenter
import Solution

fun main() = Presenter.present(Solution03)

object Solution03 : Solution {
    private fun error(items: String): Char {
        val bound = items.length / 2
        val items1 = items.substring(0, bound).toSet()
        val items2 = items.substring(bound).toSet()
        val common = items1 intersect items2
        return common.single()
    }

    private fun badge(rucksack1: String, rucksack2: String, rucksack3: String): Char {
        val items1 = rucksack1.toSet()
        val items2 = rucksack2.toSet()
        val items3 = rucksack3.toSet()
        val common = items1 intersect items2 intersect items3
        return common.single()
    }

    private fun priority(item: Char): Int = when (item) {
        in 'a'..'z' -> item - 'a' + 1
        in 'A'..'Z' -> item - 'A' + 27
        else -> throw IllegalArgumentException("Cannot determine the priority of item '$item'")
    }

    override fun part1(input: Input): String = input.lines()
        .map { error(it) }
        .sumOf { priority(it) }
        .toString()

    override fun part2(input: Input): String = input.lines()
        .chunked(3) { (r1, r2, r3) -> badge(r1, r2, r3) }
        .sumOf { priority(it) }
        .toString()
}
