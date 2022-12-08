package day04.section

data class SectionRange(
    val start: Int,
    val end: Int,
) {
    operator fun contains(range: SectionRange) = this.start <= range.start && range.end <= this.end

    infix fun overlaps(range: SectionRange): Boolean =
        if (start <= range.start) end >= range.start
        else start <= range.end
}
