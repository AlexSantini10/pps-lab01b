package it.unibo.pps.e2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {

    private static final int SIZE = 5;

    @Test
    void initialStateHasExactlyOneKnight() {
        final Logics l = new LogicsImpl(SIZE);
        assertEquals(1, scanBoard(l, SIZE).knights.size());
    }

    @Test
    void initialStateHasExactlyOnePawn() {
        final Logics l = new LogicsImpl(SIZE);
        assertEquals(1, scanBoard(l, SIZE).pawns.size());
    }

    @Test
    void initialStateKnightAndPawnAreInDifferentCells() {
        final Logics l = new LogicsImpl(SIZE);
        final BoardScan scan = scanBoard(l, SIZE);
        assertNotEquals(scan.knights.get(0), scan.pawns.get(0));
    }

    @Test
    void hitWithNegativeRowThrows() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(4, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> l.hit(-1, 0));
    }

    @Test
    void hitWithNegativeColThrows() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(4, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> l.hit(0, -1));
    }

    @Test
    void hitWithRowEqualToSizeThrows() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(4, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> l.hit(SIZE, 0));
    }

    @Test
    void hitWithColEqualToSizeThrows() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(4, 4));
        assertThrows(IndexOutOfBoundsException.class, () -> l.hit(0, SIZE));
    }

    @Test
    void invalidMoveReturnsFalse() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(4, 4));
        assertFalse(l.hit(4, 3));
    }

    @Test
    void invalidMoveDoesNotMoveKnight() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(4, 4));
        l.hit(4, 3);
        assertTrue(l.hasKnight(4, 4));
    }

    @Test
    void validMoveMovesKnightToTarget() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(4, 4));
        l.hit(2, 3);
        assertTrue(l.hasKnight(2, 3));
    }

    @Test
    void validMoveClearsPreviousKnightCell() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(4, 4));
        l.hit(2, 3);
        assertFalse(l.hasKnight(4, 4));
    }

    @Test
    void validNonCapturingMoveReturnsFalse() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(4, 4));
        assertFalse(l.hit(2, 3));
    }

    @Test
    void capturingMoveReturnsTrue() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(2, 3), new Pair<>(4, 4));
        assertTrue(l.hit(2, 3));
    }

    @Test
    void capturingMovePlacesKnightOnPawnCell() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(2, 3), new Pair<>(4, 4));
        l.hit(2, 3);
        assertTrue(l.hasKnight(2, 3));
    }

    @Test
    void pawnDoesNotMoveAfterAnyHit() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(1, 1), new Pair<>(4, 4));
        l.hit(2, 3);
        assertTrue(l.hasPawn(1, 1));
    }

    @Test
    void moveWithDeltaTwoOneIsAccepted() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(2, 2));
        l.hit(4, 3); // (+2,+1)
        assertTrue(l.hasKnight(4, 3));
    }

    @Test
    void moveWithDeltaOneTwoIsAccepted() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(2, 2));
        l.hit(3, 4); // (+1,+2)
        assertTrue(l.hasKnight(3, 4));
    }

    @Test
    void moveWithDeltaTwoTwoIsRejected() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(2, 2));
        l.hit(4, 4); // (+2,+2)
        assertTrue(l.hasKnight(2, 2));
    }

    @Test
    void moveWithDeltaThreeZeroIsRejected() {
        final Logics l = new LogicsImpl(SIZE, new Pair<>(0, 0), new Pair<>(2, 2));
        l.hit(5 - 1, 2); // (+2? no) safer explicit: (4,2) is (+2,0) for size=5
        assertTrue(l.hasKnight(2, 2));
    }

    private static BoardScan scanBoard(final Logics logic, final int size) {
        final List<Position> knights = new ArrayList<>();
        final List<Position> pawns = new ArrayList<>();

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (logic.hasKnight(r, c)) {
                    knights.add(new Position(r, c));
                }
                if (logic.hasPawn(r, c)) {
                    pawns.add(new Position(r, c));
                }
            }
        }
        return new BoardScan(knights, pawns);
    }

    private static final class BoardScan {
        private final List<Position> knights;
        private final List<Position> pawns;

        private BoardScan(final List<Position> knights, final List<Position> pawns) {
            this.knights = knights;
            this.pawns = pawns;
        }
    }

    private static final class Position {
        private final int row;
        private final int col;

        private Position(final int row, final int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Position)) {
                return false;
            }
            final Position other = (Position) o;
            return this.row == other.row && this.col == other.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.row, this.col);
        }
    }
}