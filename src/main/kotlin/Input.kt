import java.nio.file.Files
import java.nio.file.Paths

class Input(clazz: Class<*>, name: String = "input.txt") {
    private val lines: List<String> = clazz.getResource(name)!!
        .toURI()!!
        .let { Paths.get(it) }
        .let { Files.lines(it) }
        .toList()

    fun lines(): List<String> = lines
}
