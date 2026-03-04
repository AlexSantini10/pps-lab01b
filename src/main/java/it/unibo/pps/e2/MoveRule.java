package it.unibo.pps.e2;

public interface MoveRule {
    boolean isValid(Pair<Integer, Integer> from, Pair<Integer, Integer> to);
}
