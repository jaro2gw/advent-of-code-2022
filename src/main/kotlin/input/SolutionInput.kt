package input

import Input
import java.nio.file.Files
import java.nio.file.Paths

class SolutionInput(clazz: Class<*>, name: String = "input.txt") : Input {
    private val lines = clazz.getResource(name)!!
        .toURI()!!
        .let { Paths.get(it) }
        .let { Files.lines(it) }
        .toList()

    override fun lines() = lines.asSequence()
}
