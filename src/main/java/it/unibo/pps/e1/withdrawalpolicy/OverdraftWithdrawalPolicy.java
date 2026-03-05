package it.unibo.pps.e1.withdrawalpolicy;

public class OverdraftWithdrawalPolicy implements WithdrawalPolicy {

    private final int overdraftLimit;

    public OverdraftWithdrawalPolicy(final int overdraftLimit) {
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit must be non-negative");
        }
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public boolean canWithdraw(final int currentBalance, final int totalDebit) {
        if (totalDebit < 0) {
            throw new IllegalArgumentException("Total debit must be non-negative");
        }

        return currentBalance - totalDebit >= -this.overdraftLimit;
    }
}