package it.unibo.pps.e2;

import java.util.Random;

public class RandomPlacementStrategy implements PlacementStrategy {

    private final Random random;

    public RandomPlacementStrategy(final Random random) {
        this.random = random;
    }

    @Override
    public Pair<Integer,Integer> pawnPosition(final int size) {
        return randomPosition(size);
    }

    @Override
    public Pair<Integer,Integer> knightPosition(final int size, final Pair<Integer,Integer> pawn) {
        return randomPosition(size);
    }

    private Pair<Integer,Integer> randomPosition(final int size) {
        return new Pair<>(this.random.nextInt(size), this.random.nextInt(size));
    }
}