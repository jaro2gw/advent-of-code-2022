package day22

import Input
import Presenter
import Solution
import day22.board.Board
import day22.board.Tile
import day22.board.Turn
import utils.Coords
import utils.Direction
import utils.Direction.EAST
import utils.Direction.NORTH
import utils.Direction.SOUTH
import utils.Direction.WEST
import utils.NUMBER_PATTERN
import utils.split

fun main() = Presenter.present(Solution22)

object Solution22 : Solution {
    private val MOVE_REGEX = Regex(NUMBER_PATTERN)
    private val TURN_REGEX = Regex("([LR])")

    private fun turns(line: String): List<Turn> = TURN_REGEX.findAll(line)
        .map { it.groupValues[1] }
        .map { it.single() }
        .map { Turn.fromChar(it) }
        .toList()

    private fun moves(line: String): List<Int> = MOVE_REGEX.findAll(line)
        .map { it.groupValues[1] }
        .map { it.toInt() }
        .toList()

    private fun pad(lines: List<String>): List<String> {
        val width = lines.maxOf { it.length }
        return lines.map { it.padEnd(width, ' ') }
    }

    private fun tiles(lines: List<String>): Array<Array<Tile>> = pad(lines)
        .map { line ->
            line.map { Tile.fromChar(it) }.toTypedArray()
        }
        .toTypedArray()

    private fun convert(input: Input): Triple<Array<Array<Tile>>, List<Int>, List<Turn>> {
        val (lines1, lines2) = split(input).toList()

        val tiles = tiles(lines1)
        val moves = moves(lines2.single())
        val turns = turns(lines2.single())

        return Triple(tiles, moves, turns)
    }

    private fun score(direction: Direction) = when (direction) {
        EAST -> 0
        SOUTH -> 1
        WEST -> 2
        NORTH -> 3
    }

    private fun score(coords: Coords): Int = 1000 * (coords.row + 1) + 4 * (coords.col + 1)

    private fun score(coords: Coords, direction: Direction): Int = score(coords) + score(direction)

    override fun part1(input: Input): String {
        val (tiles, moves, turns) = convert(input)
        val (coords, direction) = Board().endpoint(tiles, moves, turns)
        return score(coords, direction).toString()
    }

    override fun part2(input: Input): String {
        TODO()
    }
}
