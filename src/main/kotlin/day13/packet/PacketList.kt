package day13.packet

data class PacketList(val packets: List<Packet>) : Packet() {
    constructor(packet: Packet) : this(packets = listOf(packet))

    override infix operator fun compareTo(other: Packet): Int {
        when (other) {
            is PacketData -> return compareTo(other = PacketList(other))
            is PacketList -> {
                val iterL = packets.iterator()
                val iterR = other.packets.iterator()

                var nextL: Packet
                var nextR: Packet

                while (iterL.hasNext() && iterR.hasNext()) {
                    nextL = iterL.next()
                    nextR = iterR.next()

                    val comp = nextL compareTo nextR
                    if (comp != 0) return comp
                }

                return when {
                    iterR.hasNext() -> -1
                    iterL.hasNext() -> 1
                    else            -> 0
                }
            }
        }
    }
}
