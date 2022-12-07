package day07.file.system

open class FileSystemDirectory(
    name: String,
    parent: FileSystemDirectory? = null,
) : FileSystemNode(name, parent), Iterable<FileSystemNode> {
    private val children: MutableMap<String, FileSystemNode> = mutableMapOf()

    override fun size(): Long = children.values.sumOf { it.size() }

    override fun toString(): String = buildString {
        appendLine("- $name (dir, size=${size()})")
        children.values.map { it.toString() }
            .flatMap { it.split('\n') }
            .filterNot { it.isBlank() }
            .forEach { appendLine("\t$it") }
    }

    operator fun get(name: String): FileSystemNode? = children[name]

    fun directory(name: String) {
        children[name] = FileSystemDirectory(name, this)
    }

    fun file(name: String, size: Long) {
        children[name] = FileSystemFile(name, size, this)
    }

    override fun iterator(): Iterator<FileSystemNode> = children.values.iterator()
}
