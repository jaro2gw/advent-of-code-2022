package day07.file.system

import day07.command.ChangeDirectoryCommand
import day07.command.Command
import day07.command.ListCommand

object FileSystemReconstructor {
    private val COMMAND_REGEX_LIST = Regex("\\$ ls")
    private val COMMAND_REGEX_CHANGE_DIRECTORY = Regex("\\$ cd (.+)")

    private val NODE_REGEX_DIRECTORY = Regex("dir (.+)")
    private val NODE_REGEX_FILE = Regex("(\\d+) (.+)")

    private fun command(line: String): Command {
        val listMatchResult = COMMAND_REGEX_LIST.find(line)
        if (listMatchResult != null) return ListCommand

        val changeDirectoryMatchResult = COMMAND_REGEX_CHANGE_DIRECTORY.find(line)
        if (changeDirectoryMatchResult != null) {
            val directory = changeDirectoryMatchResult.groupValues[1]
            return ChangeDirectoryCommand(directory)
        }

        throw IllegalArgumentException("Could not recognise command '$line'")
    }

    private fun node(line: String): (FileSystemDirectory) -> Unit {
        val directoryMatchResult = NODE_REGEX_DIRECTORY.find(line)
        if (directoryMatchResult != null) {
            val name = directoryMatchResult.groupValues[1]
            return { it.directory(name) }
        }

        val fileMatchResult = NODE_REGEX_FILE.find(line)
        if (fileMatchResult != null) {
            val size = fileMatchResult.groupValues[1].toLong()
            val name = fileMatchResult.groupValues[2]
            return { it.file(name, size) }
        }

        throw IllegalArgumentException("Could not recognise node '$line'")
    }

    fun reconstruct(output: Iterable<String>): FileSystemDirectory {
        val root = FileSystemDirectory("/")
        var directory = root
        output.forEach { line ->
            if (line.startsWith('$')) {
                val command = command(line)
                if (command is ChangeDirectoryCommand) directory = when (command.directory) {
                    "/" -> root
                    ".." -> directory.parent!!
                    else -> directory[command.directory] as FileSystemDirectory
                }
            } else {
                node(line).invoke(directory)
            }
        }
        return root
    }
}
