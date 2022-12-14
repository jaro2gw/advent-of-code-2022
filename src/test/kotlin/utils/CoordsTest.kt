package utils

import org.junit.jupiter.api.Test
import kotlin.test.assertContentEquals

class CoordsTest {
    @Test
    fun `should produce single element path if start and end are the same`() {
        val coords = Coords(0, 0)

        assertContentEquals(
            expected = sequenceOf(
                Coords(0, 0)
            ),
            actual = Coords.path(coords, coords),
        )
    }

    @Test
    fun `should produce straight path if rows are the same`() {
        val start = Coords(0, 0)
        val end = Coords(0, 5)

        assertContentEquals(
            expected = sequenceOf(
                Coords(0, 0),
                Coords(0, 1),
                Coords(0, 2),
                Coords(0, 3),
                Coords(0, 4),
                Coords(0, 5),
            ),
            actual = Coords.path(start, end)
        )
    }

    @Test
    fun `should produce straight path if cols are the same`() {
        val start = Coords(0, 0)
        val end = Coords(5, 0)

        assertContentEquals(
            expected = sequenceOf(
                Coords(0, 0),
                Coords(1, 0),
                Coords(2, 0),
                Coords(3, 0),
                Coords(4, 0),
                Coords(5, 0),
            ),
            actual = Coords.path(start, end)
        )
    }

    @Test
    fun `should produce diagonal path if neither rows nor cols are the same`() {
        val start = Coords(0, 0)
        val end = Coords(5, 5)

        assertContentEquals(
            expected = sequenceOf(
                Coords(0, 0),
                Coords(1, 1),
                Coords(2, 2),
                Coords(3, 3),
                Coords(4, 4),
                Coords(5, 5),
            ),
            actual = Coords.path(start, end)
        )
    }

    @Test
    fun `should combine diagonal and straight path`() {
        val start = Coords(0, 0)
        val end = Coords(9, 5)

        assertContentEquals(
            expected = sequenceOf(
                Coords(0, 0),
                Coords(1, 1),
                Coords(2, 2),
                Coords(3, 3),
                Coords(4, 4),
                Coords(5, 5),
                Coords(6, 5),
                Coords(7, 5),
                Coords(8, 5),
                Coords(9, 5),
            ),
            actual = Coords.path(start, end)
        )
    }
}
