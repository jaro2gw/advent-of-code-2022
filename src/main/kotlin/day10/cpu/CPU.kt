package day10.cpu

import day10.command.AddxCommand
import day10.command.Command
import day10.command.NoopCommand

class CPU(private val monitor: (State) -> Unit) {
    data class State(val clock: Int, val register: Int)

    private var state = State(1, 1)
        set(value) {
            monitor(field)
            field = value
        }

    private fun tick(addx: Int = 0) {
        state = State(
            clock = state.clock + 1,
            register = state.register + addx
        )
    }

    fun execute(command: Command) = when (command) {
        is NoopCommand -> tick()
        is AddxCommand -> {
            tick()
            tick(command.number)
        }
    }
}
