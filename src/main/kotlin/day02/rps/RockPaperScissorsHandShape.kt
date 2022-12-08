package day02.rps

enum class RockPaperScissorsHandShape(val score: Int) {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);

    val winner get() = WINNER.getValue(this)
    val loser get() = LOSER.getValue(this)

    companion object {
        private val WINNER = mapOf(
            ROCK to PAPER,
            PAPER to SCISSORS,
            SCISSORS to ROCK,
        )
        private val LOSER = mapOf(
            ROCK to SCISSORS,
            PAPER to ROCK,
            SCISSORS to PAPER,
        )
    }
}
