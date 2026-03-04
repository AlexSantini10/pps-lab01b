package it.unibo.pps.e2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MoveRuleTest {

    private static final MoveRule RULE = new StandardKnightMoveRule();
    private static final Pair<Integer, Integer> FROM = new Pair<>(4, 4);

    @Test
    void acceptsTwoOneMove() {
        assertTrue(RULE.isValid(FROM, new Pair<>(2, 3)));
    }

    @Test
    void acceptsOneTwoMove() {
        assertTrue(RULE.isValid(FROM, new Pair<>(3, 2)));
    }

    @Test
    void rejectsAdjacentMove() {
        assertFalse(RULE.isValid(FROM, new Pair<>(4, 3)));
    }

    @Test
    void rejectsSameCell() {
        assertFalse(RULE.isValid(FROM, new Pair<>(4, 4)));
    }

    @Test
    void rejectsThreeZeroMove() {
        assertFalse(RULE.isValid(FROM, new Pair<>(1, 4)));
    }
}