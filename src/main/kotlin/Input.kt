import java.nio.file.Files
import java.nio.file.Paths

class Input(clazz: Class<*>, file: String) {
    private val lines: List<String> = clazz.getResource(file)!!
        .toURI()!!
        .let { Paths.get(it) }
        .let { Files.lines(it) }
        .toList()

    fun lines(): List<String> = lines
}
