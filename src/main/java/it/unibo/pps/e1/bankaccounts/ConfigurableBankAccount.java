package it.unibo.pps.e1.bankaccounts;

import it.unibo.pps.e1.withdrawalfees.WithdrawalFee;
import it.unibo.pps.e1.withdrawalpolicies.WithdrawalPolicy;

import java.util.Objects;

public class ConfigurableBankAccount implements BankAccount {

    private final BankAccount base;
    private final WithdrawalFee withdrawalFee;
    private final WithdrawalPolicy withdrawalPolicy;

    public ConfigurableBankAccount(final BankAccount base, final WithdrawalFee withdrawalFee, final WithdrawalPolicy withdrawalPolicy) {
        this.base = Objects.requireNonNull(base);
        this.withdrawalFee = Objects.requireNonNull(withdrawalFee);
        this.withdrawalPolicy = Objects.requireNonNull(withdrawalPolicy);
    }

    @Override
    public int getBalance() {
        return this.base.getBalance();
    }

    @Override
    public void deposit(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Deposit amount must be non-negative");
        }

        this.base.deposit(amount);
    }

    @Override
    public void withdraw(final int amount) {
        final int fee = this.withdrawalFee.getFee(amount);
        final int totalDebit = amount + fee;
        final int currentBalance = this.base.getBalance();

        if (!this.withdrawalPolicy.canWithdraw(currentBalance, totalDebit)) {
            throw new IllegalStateException("Withdrawal not permitted");
        }

        this.base.withdraw(totalDebit);
    }
}