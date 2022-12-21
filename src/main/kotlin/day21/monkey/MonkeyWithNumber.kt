package day21.monkey

class MonkeyWithNumber(
    name: String,
    private val number: Double,
) : Monkey(name) {
    override fun yell(): Double = number
}
