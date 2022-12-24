package day22.board

import day22.board.Direction.EAST
import day22.board.Tile.BLOCKED
import day22.board.Tile.BORDER
import day22.board.Tile.EMPTY
import utils.Coords
import utils.get

class Board {
    private fun start(tiles: Array<Array<Tile>>): Coords {
        val row = 0
        val col = tiles[row].indexOf(EMPTY)
        return Coords(row, col)
    }

    private fun next(
        tiles: Array<Array<Tile>>,
        position: Coords,
        direction: Direction
    ): Coords? {
        val (r, c) = position + direction.vector
        val row = (tiles.size + r) % tiles.size
        val col = (tiles[row].size + c) % tiles[row].size
        val pos = Coords(row, col)
        return when (tiles[pos]) {
            BLOCKED -> null
            EMPTY -> pos
            BORDER -> next(tiles, pos, direction)
        }
    }

    private fun position(
        tiles: Array<Array<Tile>>,
        position: Coords,
        direction: Direction,
        steps: Int
    ): Coords = generateSequence(position) { next(tiles, it, direction) }
        .take(steps + 1)
        .last()

    fun endpoint(tiles: Array<Array<Tile>>, moves: List<Int>, turns: List<Turn>): Pair<Coords, Direction> {
        var direction = EAST
        var position = position(
            tiles,
            start(tiles),
            direction,
            moves.first(),
        )

        moves.drop(1).zip(turns).forEach { (steps, turn) ->
            direction = direction.turn(turn)
            position = position(
                tiles,
                position,
                direction,
                steps,
            )
        }

        return position to direction
    }
}
