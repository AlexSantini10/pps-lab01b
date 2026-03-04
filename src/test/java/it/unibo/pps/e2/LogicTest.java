package it.unibo.pps.e2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicTest {

    private static final int SIZE = 5;

    private static final Pair<Integer, Integer> PAWN = new Pair<>(2, 2);
    private static final Pair<Integer, Integer> KNIGHT = new Pair<>(4, 3);

    private static final Pair<Integer, Integer> VALID_KNIGHT_TARGET = new Pair<>(3, 1);
    private static final Pair<Integer, Integer> INVALID_KNIGHT_TARGET = KNIGHT;

    private static final Board BOARD = new StandardBoard(SIZE);
    private static final MoveRule RULE = new StandardKnightMoveRule();

    private static Logics logicWithDefaultPieces() {
        return new LogicsImpl(BOARD, RULE, PAWN, KNIGHT);
    }

    private static Logics logicWithPieces(final Pair<Integer, Integer> pawn, final Pair<Integer, Integer> knight) {
        return new LogicsImpl(BOARD, RULE, pawn, knight);
    }

    @Test
    void pawnIsReportedAtItsInitialPosition() {
        assertTrue(logicWithDefaultPieces().hasPawn(PAWN.getX(), PAWN.getY()));
    }

    @Test
    void knightIsReportedAtItsInitialPosition() {
        assertTrue(logicWithDefaultPieces().hasKnight(KNIGHT.getX(), KNIGHT.getY()));
    }

    @Test
    void hitOnInvalidMoveReturnsFalse() {
        assertFalse(logicWithDefaultPieces().hit(INVALID_KNIGHT_TARGET.getX(), INVALID_KNIGHT_TARGET.getY()));
    }

    @Test
    void hitOnValidMoveUpdatesKnightPosition() {
        final Logics l = logicWithDefaultPieces();
        l.hit(VALID_KNIGHT_TARGET.getX(), VALID_KNIGHT_TARGET.getY());
        assertTrue(l.hasKnight(VALID_KNIGHT_TARGET.getX(), VALID_KNIGHT_TARGET.getY()));
    }

    @Test
    void hitOnPawnReturnsTrue() {
        assertTrue(logicWithDefaultPieces().hit(PAWN.getX(), PAWN.getY()));
    }

    @Test
    void hitOutsideBoardThrows() {
        assertThrows(IndexOutOfBoundsException.class, () -> logicWithDefaultPieces().hit(-1, 0));
    }

    @Test
    void constructorRejectsOverlappingPieces() {
        assertThrows(IllegalArgumentException.class, () -> logicWithPieces(PAWN, PAWN));
    }

    @Test
    void constructorRejectsPawnOutsideBoard() {
        assertThrows(IndexOutOfBoundsException.class, () -> logicWithPieces(new Pair<>(-1, 0), KNIGHT));
    }

    @Test
    void constructorRejectsKnightOutsideBoard() {
        assertThrows(IndexOutOfBoundsException.class, () -> logicWithPieces(PAWN, new Pair<>(0, SIZE)));
    }
}