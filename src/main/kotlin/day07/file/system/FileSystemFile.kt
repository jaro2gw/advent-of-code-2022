package day07.file.system

class FileSystemFile(
    override val name: String,
    val size: Long,
    override val parent: FileSystemDirectory,
) : FileSystemNode(name, parent) {
    override fun size(): Long = size

    override fun toString(): String = "- $name (file, size=$size)"
}
