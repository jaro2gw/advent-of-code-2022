package day17;

import Input
import Presenter
import Solution
import day17.rock.RockChamber
import day17.rock.RockMove
import day17.rock.RockShape.Companion.shapes

fun main() = Presenter.present(Solution17)

object Solution17 : Solution {
    private fun moves(input: Input): List<RockMove> = input.lines()
        .first()
        .map {
            when (it) {
                '<'  -> RockMove.LEFT
                '>'  -> RockMove.RIGHT
                else -> throw IllegalArgumentException("Cannot convert char '$it' into a rock move")
            }
        }

    override fun part1(input: Input): String = RockChamber(
        width = 7,
        shapes = shapes,
        moves = moves(input)
    )
        .height(2022)
        .toString()

    override fun part2(input: Input): String = RockChamber(
        width = 7,
        shapes = shapes,
        moves = moves(input)
    )
        .height(1_000_000_000_000L)
        .toString()
}
