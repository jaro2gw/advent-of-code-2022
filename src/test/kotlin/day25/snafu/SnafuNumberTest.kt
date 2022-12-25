package day25.snafu

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SnafuNumberTest {
    private val snafu: List<String> = listOf(
        "1=-0-2",
        "12111",
        "2=0=",
        "21",
        "2=01",
        "111",
        "20012",
        "112",
        "1=-1=",
        "1-12",
        "12",
        "1=",
        "122",
    )

    private val decimal: List<Long> = listOf(
        1747,
        906,
        198,
        11,
        201,
        31,
        1257,
        32,
        353,
        107,
        7,
        3,
        37,
    )

    @Test
    fun `should convert to decimal`() {
        snafu.zip(decimal).forEach { (snf, dec) ->
            val num = SnafuNumber.toLong(snf)
            assertEquals(dec, num)
        }
    }

    @Test
    fun `should convert to snafu`() {
        decimal.zip(snafu).forEach { (dec, snf) ->
            val num = SnafuNumber.fromLong(dec)
            assertEquals(snf, num)
        }
    }
}
