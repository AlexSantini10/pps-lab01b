package it.unibo.pps.e1;

public class SilverBankAccount implements BankAccount {

    private final BankAccount base;
    private final WithdrawalFee feePolicy;
    private final WithdrawalPolicy withdrawalPolicy;

    SilverBankAccount(
            final BankAccount base,
            final WithdrawalFee feePolicy,
            final WithdrawalPolicy withdrawalPolicy
    ) {
        this.base = base;
        this.feePolicy = feePolicy;
        this.withdrawalPolicy = withdrawalPolicy;
    }

    @Override
    public int getBalance() {
        return this.base.getBalance();
    }

    @Override
    public void deposit(final int amount) {
        this.base.deposit(amount);
    }

    @Override
    public void withdraw(final int amount) {
        final int fee = this.feePolicy.getFee(amount);
        final int totalDebit = amount + fee;

        if (!this.withdrawalPolicy.canWithdraw(this.getBalance(), totalDebit)) {
            throw new IllegalStateException();
        }

        this.base.withdraw(totalDebit);
    }
}