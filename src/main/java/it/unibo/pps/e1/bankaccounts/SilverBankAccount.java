package it.unibo.pps.e1.bankaccounts;

import it.unibo.pps.e1.withdrawalfee.FixedWithdrawalFee;
import it.unibo.pps.e1.withdrawalpolicy.StandardWithdrawalPolicy;

public final class SilverBankAccount extends ConfigurableBankAccount {

    public SilverBankAccount(final BankAccount base) {
        super(base, new FixedWithdrawalFee(1), new StandardWithdrawalPolicy());
    }
}