package day04

import Input
import Presenter
import Solution
import day04.section.SectionRange

fun main() = Presenter.present(Solution04)

object Solution04 : Solution {
    private val SECTIONS = Regex("^(\\d+)-(\\d+),(\\d+)-(\\d+)$")

    private fun sections(line: String): Pair<SectionRange, SectionRange> {
        val (s1, e1, s2, e2) = SECTIONS.find(line)!!
            .groupValues
            .drop(1)
            .map { it.toInt() }

        return SectionRange(s1, e1) to SectionRange(s2, e2)
    }

    override fun part1(input: Input): String = input.lines()
        .map { sections(it) }
        .count { (s1, s2) -> s1 in s2 || s2 in s1 }
        .toString()

    override fun part2(input: Input): String = input.lines()
        .map { sections(it) }
        .count { (s1, s2) -> s1 overlaps s2 }
        .toString()
}
