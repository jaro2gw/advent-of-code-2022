package day15.sensor

/** [upper] inclusive */
data class Range(
    val lower: Int,
    val upper: Int
) : Comparable<Range> {
    init {
        require(lower <= upper) { "Range lower bound cannot be higher than the upper bound" }
    }

    fun size(): Int = upper - lower + 1

    operator fun contains(value: Int): Boolean = value in lower..upper

    override fun compareTo(other: Range): Int {
        val comp = this.lower.compareTo(other.lower)
        return if (comp != 0) comp
        else this.upper.compareTo(other.upper)
    }

    override fun toString(): String = "($lower..$upper)"
}
