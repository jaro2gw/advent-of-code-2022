package day14.utils

import kotlin.math.max
import kotlin.math.min

data class MinMax(
    val min: Int,
    val max: Int,
) {
    init {
        require(min <= max)
    }

    constructor(value: Int) : this(
        min = value,
        max = value,
    )

    fun extend(value: Int) = MinMax(
        min = min(this.min, value),
        max = max(this.max, value),
    )
}
