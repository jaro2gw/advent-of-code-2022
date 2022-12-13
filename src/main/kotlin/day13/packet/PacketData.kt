package day13.packet

data class PacketData(val data: Int) : Packet() {
    override infix operator fun compareTo(other: Packet): Int = when (other) {
        is PacketData -> data.compareTo(other.data)
        is PacketList -> PacketList(packet = this) compareTo other
    }
}
