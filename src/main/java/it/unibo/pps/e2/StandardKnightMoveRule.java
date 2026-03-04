package it.unibo.pps.e2;

public class StandardKnightMoveRule implements MoveRule {
    @Override
    public boolean isValid(Pair<Integer, Integer> from, Pair<Integer, Integer> to) {
        int x = to.getX() - from.getX();
        int y = to.getY() - from.getY();
        return x != 0 && y != 0 && Math.abs(x) + Math.abs(y) == 3;
    }
}
