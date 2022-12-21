package day21;

import Input
import Presenter
import Solution
import day21.monkey.Monkey
import day21.monkey.MonkeyWithNumber
import day21.monkey.MonkeyWithOperation
import day21.operation.Operation
import utils.NUMBER_PATTERN

fun main() = Presenter.present(Solution21)

private typealias Numbers = Map<String, Double>
private typealias Operations = Map<String, Triple<String, Char, String>>

object Solution21 : Solution {
    private const val MONKEY_NAME_PATTERN = "([a-z]{4})"
    private val MONKEY_WITH_NUMBER_REGEX = Regex(
        "$MONKEY_NAME_PATTERN: $NUMBER_PATTERN"
    )
    private val MONKEY_WITH_OPERATION_REGEX = Regex(
        "$MONKEY_NAME_PATTERN: $MONKEY_NAME_PATTERN ([-+*/]) $MONKEY_NAME_PATTERN"
    )

    private fun monkeys(input: Input): Pair<Numbers, Operations> {
        val numbers = mutableMapOf<String, Double>()
        val operations = mutableMapOf<String, Triple<String, Char, String>>()

        input.lines().forEach { line ->
            val monkeyWithNumber = MONKEY_WITH_NUMBER_REGEX.find(line)
            if (monkeyWithNumber != null) {
                val name = monkeyWithNumber.groupValues[1]
                val number = monkeyWithNumber.groupValues[2].toDouble()

                numbers[name] = number
            }
            else {
                val monkeyWithOperation = MONKEY_WITH_OPERATION_REGEX.find(line)!!

                val name = monkeyWithOperation.groupValues[1]
                val name1 = monkeyWithOperation.groupValues[2]
                val char = monkeyWithOperation.groupValues[3].single()
                val name2 = monkeyWithOperation.groupValues[4]

                operations[name] = Triple(name1, char, name2)
            }
        }

        return numbers to operations
    }

    private fun monkey(name: String, numbers: Numbers, operations: Operations): Monkey {
        val number = numbers[name]
        return if (number != null) MonkeyWithNumber(name, number)
        else {
            val (name1, char, name2) = operations[name]!!
            val monkey1 = monkey(name1, numbers, operations)
            val monkey2 = monkey(name2, numbers, operations)
            val operation = Operation.fromChar(char)
            MonkeyWithOperation(name, monkey1, monkey2, operation)
        }
    }

    override fun part1(input: Input): String {
        val (numbers, operations) = monkeys(input)
        val root = monkey("root", numbers, operations)
        return root.yell().toLong().toString()
    }

    override fun part2(input: Input): String {
        val (numbers, operations) = monkeys(input)
        val root = monkey("root", numbers, operations) as MonkeyWithOperation
        val santa = root.find("humn")!!
        return root.figureOutWhatMonkeyShouldYell(santa).toLong().toString()
    }
}
