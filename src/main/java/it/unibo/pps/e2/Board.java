package it.unibo.pps.e2;

public interface Board {

    int size();

    boolean isInside(int row, int col);

    void ensureInside(int row, int col);
}