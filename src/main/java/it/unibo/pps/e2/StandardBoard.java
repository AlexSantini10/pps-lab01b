package it.unibo.pps.e2;

public class StandardBoard implements Board {

    private final int size;

    public StandardBoard(final int size) {
        this.size = size;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isInside(final int row, final int col) {
        return row >= 0 && col >= 0 && row < this.size && col < this.size;
    }

    @Override
    public void ensureInside(final int row, final int col) {
        if (!this.isInside(row, col)) {
            throw new IndexOutOfBoundsException();
        }
    }
}