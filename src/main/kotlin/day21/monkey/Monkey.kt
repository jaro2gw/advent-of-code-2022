package day21.monkey

sealed class Monkey(
    val name: String
) {
    abstract fun yell(): Double
    open fun find(name: String): Monkey? =
        if (this.name == name) this
        else null

    override fun equals(other: Any?): Boolean =
        this === other ||
        other is Monkey &&
        name == other.name

    override fun hashCode(): Int = name.hashCode()

    override fun toString(): String = "Monkey($name)"
}

