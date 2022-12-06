import java.nio.file.Files
import java.nio.file.Paths

class Input(clazz: Class<*>) {
    private val lines: List<String> = clazz.getResource("input.txt")!!
        .toURI()!!
        .let { Paths.get(it) }
        .let { Files.lines(it) }
        .toList()

    fun lines(): List<String> = lines
}
