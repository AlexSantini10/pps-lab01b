package it.unibo.pps.e2;

import java.util.Objects;
import java.util.Random;

public class LogicsImpl implements Logics {

    private final Pair<Integer, Integer> pawn;
    private Pair<Integer, Integer> knight;

    private final Board board;
    private final MoveRule moveRule;

    public LogicsImpl(final int size) {
        final Board b = new StandardBoard(size);
        final MoveRule r = new StandardKnightMoveRule();
        final PlacementStrategy ps = new RandomPlacementStrategy(new Random());

        final Pair<Integer,Integer> p = ps.pawnPosition(size);
        Pair<Integer,Integer> k = ps.knightPosition(size, p);
        while (p.equals(k)) {
            k = ps.knightPosition(size, p);
        }

        this.board = b;
        this.moveRule = r;
        this.pawn = p;
        this.knight = k;
    }

    public LogicsImpl(final Board board, final MoveRule moveRule, final Pair<Integer, Integer> pawn, final Pair<Integer, Integer> knight) {
        this.board = Objects.requireNonNull(board);
        this.moveRule = Objects.requireNonNull(moveRule);
        this.pawn = Objects.requireNonNull(pawn);
        this.knight = Objects.requireNonNull(knight);

        this.board.ensureInside(this.pawn.getX(), this.pawn.getY());
        this.board.ensureInside(this.knight.getX(), this.knight.getY());

        if (this.pawn.equals(this.knight)) {
            throw new IllegalArgumentException("pawn and knight cannot overlap");
        }
    }

    @Override
    public boolean hit(final int row, final int col) {
        this.board.ensureInside(row, col);
        final Pair<Integer, Integer> target = new Pair<>(row, col);
        if (!this.moveRule.isValid(this.knight, target)) {
            return false;
        }
        this.knight = target;
        return this.pawn.equals(this.knight);
    }

    @Override
    public boolean hasKnight(final int row, final int col) {
        return this.knight.equals(new Pair<>(row, col));
    }

    @Override
    public boolean hasPawn(final int row, final int col) {
        return this.pawn.equals(new Pair<>(row, col));
    }
}