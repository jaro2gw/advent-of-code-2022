package day22.board

import day22.board.Tile.BLOCKED
import day22.board.Tile.BORDER
import day22.board.Tile.EMPTY
import utils.contains
import utils.get

class Cube(
    tiles: Array<Array<Tile>>,
    private val transition: (State) -> State
) : Board(tiles) {
    private fun makeTransition(state: State): State? = next(transition(state))

    override fun next(state: State): State? {
        val pos = state.position + state.direction.vector
        val new = state.copy(position = pos)
        return if (pos !in tiles) makeTransition(new)
        else when (tiles[pos]) {
            BLOCKED -> null
            EMPTY -> new
            BORDER -> makeTransition(new)
        }
    }
}
