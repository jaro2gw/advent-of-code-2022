package input

import Input

object StandardInput : Input {
    override fun lines() = generateSequence { readlnOrNull() }
}
