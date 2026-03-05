package it.unibo.pps.e1.withdrawalfees;

public class FixedWithdrawalFee implements WithdrawalFee{

    private final int fee;

    public FixedWithdrawalFee(int fee) {
        this.fee = fee;
    }

    @Override
    public int getFee(int amount) {
        return fee;
    }
}
