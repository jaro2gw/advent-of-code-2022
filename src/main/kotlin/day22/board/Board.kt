package day22.board

import day22.board.Tile.BLOCKED
import day22.board.Tile.BORDER
import day22.board.Tile.EMPTY
import utils.Coords
import utils.Direction
import utils.Direction.EAST
import utils.get

open class Board(
    protected val tiles: Array<Array<Tile>>,
) {
    data class State(
        val position: Coords,
        val direction: Direction,
    ) {
        override fun toString(): String = "$position -> $direction"
    }

    protected val start = State(
        position = Coords(
            row = 0,
            col = tiles[0].indexOf(EMPTY)
        ),
        direction = EAST
    )

    protected open fun next(state: State): State? {
        val (r, c) = state.position + state.direction.vector
        val row = (r + tiles.size) % tiles.size
        val col = (c + tiles[row].size) % tiles[row].size
        val pos = Coords(row, col)
        val copy = state.copy(position = pos)
        return when (tiles[pos]) {
            BLOCKED -> null
            EMPTY -> copy
            BORDER -> next(copy)
        }
    }

    private fun next(state: State, steps: Int): State =
        generateSequence(state) { next(it) }
            .take(steps + 1)
            .last()

    fun endpoint(
        moves: List<Int>,
        turns: List<Turn>
    ): State {
        var state = next(start, moves[0])

        moves.drop(1).zip(turns).forEach { (steps, turn) ->
            state = state.copy(direction = turn(state.direction))
            state = next(state, steps)
        }

        return state
    }
}
