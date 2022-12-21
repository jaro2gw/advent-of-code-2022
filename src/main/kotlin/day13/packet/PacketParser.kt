package day13.packet

import day13.packet.PacketTokenizer.Token
import day13.packet.PacketTokenizer.Token.BRACKET_L
import day13.packet.PacketTokenizer.Token.BRACKET_R
import day13.packet.PacketTokenizer.Token.NUMBER

class PacketParser(
    private val tokenizer: PacketTokenizer
) {
    private fun data(token: NUMBER) = PacketData(token.number)

    private fun list(tokens: Iterator<Token>): PacketList {
        val packets = mutableListOf<Packet>()

        while (tokens.hasNext()) {
            when (val token = tokens.next()) {
                BRACKET_L -> packets += list(tokens)
                is NUMBER -> packets += data(token)
                BRACKET_R -> return PacketList(packets)
            }
        }

        throw IllegalStateException("Parsing exception: did not encounter a matching closing bracket")
    }

    private fun parse(tokens: Iterator<Token>): Packet = when (val token = tokens.next()) {
        BRACKET_L -> list(tokens)
        is NUMBER -> data(token)
        is BRACKET_R -> throw IllegalStateException("Parsing exception: encountered an unmatched closing bracket")
    }

    fun parse(string: String): Packet {
        val tokens = tokenizer.tokenize(string).iterator()
        check(tokens.hasNext()) { "Parsing exception: there are no tokens in the sequence" }
        val packet = parse(tokens)
        check(!tokens.hasNext()) { "Parsing exception: there are tokens remaining in the sequence" }
        return packet
    }
}
