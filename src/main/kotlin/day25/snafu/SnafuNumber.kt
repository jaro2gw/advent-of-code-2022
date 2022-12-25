package day25.snafu

import java.util.LinkedList

object SnafuNumber {
    fun toLong(snafu: String): Long {
        var sum = 0L
        var pow = 1L
        for (digit in snafu.reversed()) {
            sum += when (digit) {
                '=' -> pow * -2
                '-' -> pow * -1
                '0' -> 0
                '1' -> pow
                '2' -> pow * 2
                else -> throw IllegalArgumentException("Unrecognised SNAFU digit '$digit'")
            }
            pow *= 5
        }
        return sum
    }

    private fun digits(decimal: Long): LinkedList<Long> {
        val digits = LinkedList<Long>()

        var num = decimal
        while (num != 0L) {
            digits += num % 5
            num /= 5
        }

        return digits
    }

    private fun normalise(digits: LinkedList<Long>): LinkedList<Long> {
        val digits5 = LinkedList<Long>()

        while (digits.isNotEmpty()) {
            val digit = digits.removeFirst()
            digits5 += when {
                digit > 2 -> {
                    val next = digits.poll() ?: 0
                    digits.addFirst(next + 1)
                    (digit - 5)
                }
                digit < -2 -> {
                    val next = digits.poll() ?: 0
                    digits.addFirst(next - 1)
                    (digit + 5)
                }
                else -> digit
            }
        }

        return digits5
    }

    fun fromLong(decimal: Long): String = normalise(digits = digits(decimal))
        .reversed()
        .joinToString(separator = "") {
            when (it) {
                -2L -> "="
                -1L -> "-"
                else -> "$it"
            }
        }
}
