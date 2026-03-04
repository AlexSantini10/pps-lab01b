package it.unibo.pps.e2;

public interface PlacementStrategy {
    Pair<Integer,Integer> pawnPosition(int size);
    Pair<Integer,Integer> knightPosition(int size, Pair<Integer,Integer> pawn);
}