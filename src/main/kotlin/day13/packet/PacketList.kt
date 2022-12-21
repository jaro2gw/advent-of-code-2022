package day13.packet

data class PacketList(val packets: List<Packet>) : Packet() {
    constructor(packet: Packet) : this(packets = listOf(packet))

    override infix operator fun compareTo(other: Packet): Int {
        when (other) {
            is PacketData -> return compareTo(other = PacketList(packet = other))
            is PacketList -> {
                val iterL = packets.iterator()
                val iterR = other.packets.iterator()

                while (iterL.hasNext() && iterR.hasNext()) {
                    val comp = iterL.next() compareTo iterR.next()
                    if (comp != 0) return comp
                }

                return when {
                    iterR.hasNext() -> -1
                    iterL.hasNext() -> 1
                    else -> 0
                }
            }
        }
    }
}
