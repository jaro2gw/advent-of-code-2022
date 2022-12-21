package day21.monkey

import day21.operation.Operation
import day21.operation.Operation.DIV
import day21.operation.Operation.MINUS
import day21.operation.Operation.PLUS
import day21.operation.Operation.TIMES

class MonkeyWithOperation(
    name: String,
    private val monkey1: Monkey,
    private val monkey2: Monkey,
    private val operation: Operation
) : Monkey(name) {
    private val number: Long by lazy {
        operation(monkey1.yell(), monkey2.yell())
    }

    private val dependencies: Set<Monkey> by lazy {
        val deps = mutableSetOf(monkey1, monkey2)
        if (monkey1 is MonkeyWithOperation) deps += monkey1.dependencies
        if (monkey2 is MonkeyWithOperation) deps += monkey2.dependencies
        return@lazy deps
    }

    override fun yell(): Long = number

    override fun find(name: String): Monkey? = super.find(name) ?: monkey1.find(name) ?: monkey2.find(name)

    private fun figureOutWhatMonkeyShouldYell(monkey: Monkey, match: Long): Long {
        val match1 = when (operation) {
            PLUS -> MINUS(match, monkey2.yell())
            MINUS -> PLUS(match, monkey2.yell())
            TIMES -> DIV(match, monkey2.yell())
            DIV -> TIMES(match, monkey2.yell())
        }
        val match2 = when (operation) {
            PLUS -> MINUS(match, monkey1.yell())
            MINUS -> MINUS(monkey1.yell(), match)
            TIMES -> DIV(match, monkey1.yell())
            DIV -> DIV(monkey1.yell(), match)
        }
        return when {
            monkey1 == monkey -> match1
            monkey2 == monkey -> match2
            monkey1 is MonkeyWithOperation && monkey in monkey1.dependencies ->
                monkey1.figureOutWhatMonkeyShouldYell(monkey, match1)
            monkey2 is MonkeyWithOperation && monkey in monkey2.dependencies ->
                monkey2.figureOutWhatMonkeyShouldYell(monkey, match2)
            else -> throw IllegalStateException("Neither $monkey1 nor $monkey2 depend on $monkey")
        }
    }

    fun figureOutWhatMonkeyShouldYell(monkey: Monkey): Long = when {
        monkey1 is MonkeyWithOperation && monkey in monkey1.dependencies ->
            monkey1.figureOutWhatMonkeyShouldYell(monkey, monkey2.yell())
        monkey2 is MonkeyWithOperation && monkey in monkey2.dependencies ->
            monkey2.figureOutWhatMonkeyShouldYell(monkey, monkey1.yell())
        else -> throw IllegalStateException("Neither $monkey1 nor $monkey2 depend on $monkey")
    }
}
