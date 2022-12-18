package utils

data class MinMax<T : Comparable<T>>(
    val min: T,
    val max: T,
) {
    init {
        require(min <= max)
    }

    private val range = min..max

    constructor(value: T) : this(
        min = value,
        max = value,
    )

    operator fun contains(value: T): Boolean = value in range

    operator fun plus(value: T) = MinMax(
        min = minOf(this.min, value),
        max = maxOf(this.max, value),
    )

    operator fun plus(that: MinMax<T>) = MinMax(
        min = minOf(this.min, that.min),
        max = maxOf(this.max, that.max),
    )
}
