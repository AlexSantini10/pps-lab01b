package it.unibo.pps.e2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StandardBoardTest {

    private static final int SIZE = 5;

    private static Board board() {
        return new StandardBoard(SIZE);
    }

    @Test
    void sizeReturnsConstructorValue() {
        assertEquals(SIZE, board().size());
    }

    @Test
    void isInsideReturnsTrueOnOrigin() {
        assertTrue(board().isInside(0, 0));
    }

    @Test
    void isInsideReturnsFalseOnNegativeRow() {
        assertFalse(board().isInside(-1, 0));
    }

    @Test
    void isInsideReturnsFalseOnNegativeCol() {
        assertFalse(board().isInside(0, -1));
    }

    @Test
    void isInsideReturnsFalseOnRowEqualToSize() {
        assertFalse(board().isInside(SIZE, 0));
    }

    @Test
    void isInsideReturnsFalseOnColEqualToSize() {
        assertFalse(board().isInside(0, SIZE));
    }

    @Test
    void ensureInsideThrowsOnOutsidePosition() {
        assertThrows(IndexOutOfBoundsException.class, () -> board().ensureInside(SIZE, 0));
    }
}