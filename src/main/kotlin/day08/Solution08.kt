package day08

import Input
import Presenter
import Solution
import day08.trees.Trees
import day08.trees.TreesEvaluator

fun main() = Presenter.present(Solution08)

object Solution08 : Solution {
    private fun makeTreeMap(lines: Iterable<String>): Trees = lines
        .map { line ->
            line.map { char -> char - '0' }
                .toIntArray()
        }
        .toTypedArray()
        .let { Trees(it) }

    override fun part1(input: Input): String {
        val trees = makeTreeMap(input.lines())
        return TreesEvaluator().visibleTrees(trees).toString()
    }

    override fun part2(input: Input): String {
        val trees = makeTreeMap(input.lines())
        return TreesEvaluator().scenicScore(trees).toString()
    }
}
