package it.unibo.pps.e2;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RandomPlacementStrategyTest {

    private static final int SIZE = 5;

    private static PlacementStrategy strategy() {
        return new RandomPlacementStrategy(new Random(0));
    }

    private static Board board() {
        return new StandardBoard(SIZE);
    }

    @Test
    void pawnPositionIsInsideBoard() {
        final Pair<Integer,Integer> pawn = strategy().pawnPosition(SIZE);
        assertTrue(board().isInside(pawn.getX(), pawn.getY()));
    }

    @Test
    void knightPositionIsDifferentFromPawn() {
        final Pair<Integer,Integer> pawn = new Pair<>(2, 2);
        final Pair<Integer,Integer> knight = strategy().knightPosition(SIZE, pawn);
        assertNotEquals(pawn, knight);
    }
}