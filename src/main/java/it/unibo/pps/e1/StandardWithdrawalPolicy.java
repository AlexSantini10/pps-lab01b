package it.unibo.pps.e1;

public class StandardWithdrawalPolicy implements  WithdrawalPolicy {

    @Override
    public boolean canWithdraw(int currentBalance, int totalDebit) {
        return currentBalance >= totalDebit;
    }
}
