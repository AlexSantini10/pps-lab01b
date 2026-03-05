package it.unibo.pps.e1.withdrawalpolicy;

public class StandardWithdrawalPolicy implements WithdrawalPolicy {

    @Override
    public boolean canWithdraw(final int currentBalance, final int totalDebit) {
        if (totalDebit < 0) {
            throw new IllegalArgumentException("Total debit must be non-negative");
        }

        return currentBalance >= totalDebit;
    }
}