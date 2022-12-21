package day07

import Input
import Presenter
import Solution
import day07.file.system.FileSystemDirectory
import day07.file.system.FileSystemReconstructor

fun main() = Presenter.present(Solution07)

object Solution07 : Solution {
    private fun directories(directory: FileSystemDirectory): Sequence<FileSystemDirectory> = sequence {
        yield(directory)
        directory.mapNotNull { it as? FileSystemDirectory }.forEach {
            yieldAll(directories(it))
        }
    }

    override fun part1(input: Input): String {
        val root = FileSystemReconstructor.reconstruct(input.lines())
        return directories(root).map { it.size() }
            .filter { it <= 100_000L }
            .sum()
            .toString()
    }

    override fun part2(input: Input): String {
        val root = FileSystemReconstructor.reconstruct(input.lines())
        val deviceSpace = 70_000_000L
        val requiredSpace = 30_000_000L
        val maxUsedSpace = deviceSpace - requiredSpace
        val usedSpace = root.size()
        return if (usedSpace > maxUsedSpace) {
            val minimumSize = usedSpace - maxUsedSpace
            directories(root).map { it.size() }
                .filter { it >= minimumSize }
                .min()
                .toString()
        }
        else "0"
    }
}
