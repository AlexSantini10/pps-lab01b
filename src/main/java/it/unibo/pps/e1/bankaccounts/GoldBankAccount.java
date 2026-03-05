package it.unibo.pps.e1.bankaccounts;

import it.unibo.pps.e1.withdrawalfee.FixedWithdrawalFee;
import it.unibo.pps.e1.withdrawalpolicy.OverdraftWithdrawalPolicy;

public final class GoldBankAccount extends ConfigurableBankAccount {

    private static final int OVERDRAFT_LIMIT = 500;

    public GoldBankAccount(final BankAccount base) {
        super(base, new FixedWithdrawalFee(0), new OverdraftWithdrawalPolicy(OVERDRAFT_LIMIT));
    }
}