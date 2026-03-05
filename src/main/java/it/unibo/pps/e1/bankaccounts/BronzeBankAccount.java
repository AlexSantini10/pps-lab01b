package it.unibo.pps.e1.bankaccounts;

import it.unibo.pps.e1.withdrawalpolicies.StandardWithdrawalPolicy;
import it.unibo.pps.e1.withdrawalfees.ThresholdWithdrawalFee;

public final class BronzeBankAccount extends ConfigurableBankAccount {

    private static final int FEE_THRESHOLD = 100;

    public BronzeBankAccount(final BankAccount base) {
        super(base, new ThresholdWithdrawalFee(FEE_THRESHOLD, 0, 1), new StandardWithdrawalPolicy());
    }
}