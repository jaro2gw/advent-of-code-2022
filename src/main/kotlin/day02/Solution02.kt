package day02

import Input
import Solution
import SolutionPresenter
import day02.RockPaperScissorsGameOutcome.DRAW
import day02.RockPaperScissorsGameOutcome.LOSE
import day02.RockPaperScissorsGameOutcome.WIN
import day02.RockPaperScissorsHandShape.PAPER
import day02.RockPaperScissorsHandShape.ROCK
import day02.RockPaperScissorsHandShape.SCISSORS

fun main() = SolutionPresenter.present(Solution02)

object Solution02 : Solution {
    private fun interpret1(line: String): RockPaperScissorsGameRound {
        val player1 = when (line[0]) {
            'A' -> ROCK
            'B' -> PAPER
            'C' -> SCISSORS
            else -> throw IllegalStateException("Illegal player1 character: ${line[0]}")
        }
        val player2 = when (line[2]) {
            'X' -> ROCK
            'Y' -> PAPER
            'Z' -> SCISSORS
            else -> throw IllegalStateException("Illegal player2 character: ${line[2]}")
        }
        return RockPaperScissorsGameRound(player1, player2)
    }

    private fun interpret2(line: String): RockPaperScissorsGameRound {
        val player1 = when (line[0]) {
            'A' -> ROCK
            'B' -> PAPER
            'C' -> SCISSORS
            else -> throw IllegalStateException("Illegal player1 character: ${line[0]}")
        }
        val player2 = when (line[2]) {
            'X' -> player1.loser
            'Y' -> player1
            'Z' -> player1.winner
            else -> throw IllegalStateException("Illegal player2 character: ${line[2]}")
        }
        return RockPaperScissorsGameRound(player1, player2)
    }

    private fun score(round: RockPaperScissorsGameRound): Int {
        val outcome = when (round.player1) {
            round.player2 -> DRAW
            round.player2.winner -> LOSE
            else -> WIN
        }

        return outcome.score + round.player2.score
    }

    override fun part1(input: Input): String = input.lines()
        .map { interpret1(it) }
        .sumOf { score(it) }
        .toString()

    override fun part2(input: Input): String = input.lines()
        .map { interpret2(it) }
        .sumOf { score(it) }
        .toString()
}