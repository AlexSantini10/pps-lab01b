package it.unibo.pps.e1.bankaccounts;

import it.unibo.pps.e1.withdrawalfees.FixedWithdrawalFee;
import it.unibo.pps.e1.withdrawalpolicies.StandardWithdrawalPolicy;

public final class SilverBankAccount extends ConfigurableBankAccount {

    public SilverBankAccount(final BankAccount base) {
        super(base, new FixedWithdrawalFee(1), new StandardWithdrawalPolicy());
    }
}