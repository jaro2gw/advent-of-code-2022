package utils

open class EnumArray<E : Enum<E>, T>(
    private val enums: Array<E>,
    transform: (E) -> T,
) : Iterable<Pair<E, T>> {
    private val array = ArrayList(enums.map(transform))

    operator fun get(enum: E): T = array[enum.ordinal]

    operator fun set(enum: E, value: T) {
        array[enum.ordinal] = value
    }

    fun <R> map(transform: (E, T) -> R): EnumArray<E, R> = EnumArray(enums) {
        transform(it, array[it.ordinal])
    }

    override fun iterator(): Iterator<Pair<E, T>> = enums.zip(array).iterator()

    override fun equals(other: Any?): Boolean =
        this === other ||
        other is EnumArray<*, *> &&
        enums contentEquals other.enums &&
        array == other.array

    override fun hashCode(): Int = 31 * enums.contentHashCode() + array.hashCode()

    override fun toString(): String = joinToString(
        prefix = "[",
        separator = ", ",
        postfix = "]",
    ) { (e, t) ->
        "$e: $t"
    }
}
