package day10.command

sealed class Command {
    companion object {
        private val COMMAND_REGEX_NOOP = Regex("noop")
        private val COMMAND_REGEX_ADDX = Regex("addx (-?\\d+)")

        fun fromString(string: String): Command {
            val `result noop` = COMMAND_REGEX_NOOP.find(string)
            if (`result noop` != null) return NoopCommand

            val `result addx` = COMMAND_REGEX_ADDX.find(string)
            if (`result addx` != null) {
                val number = `result addx`.groupValues[1].toInt()
                return AddxCommand(number)
            }

            throw IllegalArgumentException("Could not parse command from string \"$string\"")
        }
    }
}
