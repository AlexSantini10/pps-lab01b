package it.unibo.pps.e1.withdrawalfees;

public class ThresholdWithdrawalFee implements WithdrawalFee {

    private final int thresholdExclusive;
    private final int feeUnderThreshold;
    private final int feeAtOrAboveThreshold;

    public ThresholdWithdrawalFee(final int thresholdExclusive, final int feeUnderThreshold, final int feeAtOrAboveThreshold) {
        if (thresholdExclusive < 0 || feeUnderThreshold < 0 || feeAtOrAboveThreshold < 0) {
            throw new IllegalArgumentException("Threshold and fees must be non-negative");
        }
        this.thresholdExclusive = thresholdExclusive;
        this.feeUnderThreshold = feeUnderThreshold;
        this.feeAtOrAboveThreshold = feeAtOrAboveThreshold;
    }

    @Override
    public int getFee(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Withdrawal amount must be non-negative");
        }
        return amount < this.thresholdExclusive ? this.feeUnderThreshold : this.feeAtOrAboveThreshold;
    }
}