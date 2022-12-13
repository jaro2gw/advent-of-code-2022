package day13.packet

sealed class Packet : Comparable<Packet> {
    abstract override infix operator fun compareTo(other: Packet): Int
}
