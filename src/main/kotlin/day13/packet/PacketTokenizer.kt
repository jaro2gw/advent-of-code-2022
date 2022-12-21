package day13.packet

import day13.packet.PacketTokenizer.Token.BRACKET_L
import day13.packet.PacketTokenizer.Token.BRACKET_R
import day13.packet.PacketTokenizer.Token.NUMBER

class PacketTokenizer {
    sealed class Token {
        object BRACKET_L : Token()
        object BRACKET_R : Token()
        data class NUMBER(val number: Int) : Token()
    }

    private fun flush(builder: StringBuilder): String = builder.toString().also { builder.clear() }

    private fun number(builder: StringBuilder): NUMBER? = flush(builder).toIntOrNull()?.let { NUMBER(it) }

    fun tokenize(string: String): Sequence<Token> = sequence {
        val builder = StringBuilder()

        string.forEach { char ->
            when (char) {
                '[' -> yield(BRACKET_L)
                ']' -> {
                    val num = number(builder)
                    if (num != null) yield(num)
                    yield(BRACKET_R)
                }
                ',' -> {
                    val num = number(builder)
                    if (num != null) yield(num)
                }
                else -> builder.append(char)
            }
        }
    }
}
