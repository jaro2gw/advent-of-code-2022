package day07.file.system

sealed class FileSystemNode(
    open val name: String,
    open val parent: FileSystemDirectory? = null,
) {
    abstract fun size(): Long

    abstract override fun toString(): String
}
