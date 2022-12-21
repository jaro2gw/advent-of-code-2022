package day19.resource

class ResourceArray<T> private constructor(
    private val array: ArrayList<T>
)/* : Iterable<Pair<Resource, T>>*/ {
    companion object {
        fun <T> of(generator: (Resource) -> T): ResourceArray<T> {
            val values = Resource.values().map(generator)
            val array = ArrayList(values)
            return ResourceArray(array)
        }
    }

    init {
        require(array.size == Resource.values().size)
    }

    private fun values() = Resource.values().zip(array)

    fun <R> map(transform: (Resource, T) -> R): ResourceArray<R> {
        val values = values().map { (r, v) -> transform(r, v) }
        val array = ArrayList(values)
        return ResourceArray(array)
    }

    fun <R : Comparable<R>> maxOf(selector: (T) -> R): R = array.maxOf(selector)

    fun all(predicate: (Resource, T) -> Boolean): Boolean = values().all { (r, v) -> predicate(r, v) }

    operator fun get(resource: Resource): T = array[resource.ordinal]

    operator fun set(resource: Resource, value: T) {
        array[resource.ordinal] = value
    }

    override fun equals(other: Any?): Boolean =
        this === other ||
        other is ResourceArray<*> &&
        array == other.array

    override fun hashCode(): Int = array.hashCode()

    override fun toString(): String = values().joinToString(prefix = "[", separator = ", ", postfix = "]") { (e, v) ->
        "$e: $v"
    }
}