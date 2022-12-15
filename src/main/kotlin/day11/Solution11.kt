package day11;

import Input
import Presenter
import Solution
import day11.monkey.Monkey
import utils.NUMBER_PATTERN
import utils.split

fun main() = Presenter.present(Solution11)

object Solution11 : Solution {
    private val MONKEY_REGEX_INDEX = Regex("Monkey $NUMBER_PATTERN:")
    private val MONKEY_REGEX_ITEMS = Regex("Starting items: (($NUMBER_PATTERN(, )?)+)")
    private val MONKEY_REGEX_OPERATION = Regex("Operation: new = old ([+*]) (old|$NUMBER_PATTERN)")
    private val MONKEY_REGEX_TEST = Regex("Test: divisible by $NUMBER_PATTERN")
    private val MONKEY_REGEX_RESULT = Regex("If (true|false): throw to monkey $NUMBER_PATTERN")

    private fun index(line: String): Int = MONKEY_REGEX_INDEX.find(line)!!
        .groupValues[1]
        .toInt()

    private fun items(line: String): List<Long> = MONKEY_REGEX_ITEMS.find(line)!!
        .groupValues[1]
        .split(", ")
        .map { it.toLong() }

    private fun operation(line: String): (Long) -> Long {
        val (symbol, ref) = MONKEY_REGEX_OPERATION.find(line)!!.destructured
        val operator: (Long, Long) -> Long = when (symbol) {
            "+"  -> { num1, num2 -> num1 + num2 }
            "*"  -> { num1, num2 -> num1 * num2 }
            else -> throw IllegalStateException("Could not convert symbol \"$symbol\" into an operator")
        }

        return { old ->
            val num = if (ref == "old") old else ref.toLong()
            operator(old, num)
        }
    }

    private fun result(line: String, monkey: (Int) -> Monkey): (Long) -> Unit {
        val index = MONKEY_REGEX_RESULT.find(line)!!.groupValues[2].toInt()
        return { item -> monkey(index).catch(item) }
    }

    private fun prime(line: String): Long = MONKEY_REGEX_TEST.find(line)!!.groupValues[1].toLong()

    private fun monkeys(input: Input, divide: Long): List<Monkey> {
        val monkeys = ArrayList<Monkey>()

        split(input).forEach { lines ->
            val index = index(lines[0])
            val items = items(lines[1])
            val operation = operation(lines[2])
            val prime = prime(lines[3])
            val accept = result(lines[4], monkeys::get)
            val reject = result(lines[5], monkeys::get)

            val monkey = Monkey(items, operation, prime, accept, reject, divide)
            monkeys.add(index, monkey)
        }

        return monkeys
    }

    private fun turns(input: Input, turns: Int, divide: Long): String {
        val monkeys = monkeys(input, divide)

        repeat(turns) {
            monkeys.forEach { it.turn() }
        }

        return monkeys.map { it.inspections }
            .sortedDescending()
            .take(2)
            .reduce { m1, m2 -> m1 * m2 }
            .toString()
    }

    override fun part1(input: Input): String = turns(input, 20, 3L)

    override fun part2(input: Input): String = turns(input, 10_000, 1L)
}
