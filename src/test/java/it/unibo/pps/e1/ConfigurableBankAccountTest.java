package it.unibo.pps.e1;

import it.unibo.pps.e1.bankaccounts.*;
import it.unibo.pps.e1.withdrawalfees.FixedWithdrawalFee;
import it.unibo.pps.e1.withdrawalfees.ThresholdWithdrawalFee;
import it.unibo.pps.e1.withdrawalpolicies.OverdraftWithdrawalPolicy;
import it.unibo.pps.e1.withdrawalpolicies.StandardWithdrawalPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConfigurableBankAccountTest {

    private static final int INITIAL_DEPOSIT = 1000;

    private ConfigurableBankAccount silverConfig;

    @BeforeEach
    void init() {
        this.silverConfig = new ConfigurableBankAccount(
                new CoreBankAccount(),
                new FixedWithdrawalFee(1),
                new StandardWithdrawalPolicy()
        );
    }

    @Test
    public void testInitiallyEmpty() {
        assertEquals(0, this.silverConfig.getBalance());
    }

    @Test
    public void testCanDeposit() {
        this.silverConfig.deposit(INITIAL_DEPOSIT);
        assertEquals(INITIAL_DEPOSIT, this.silverConfig.getBalance());
    }

    @Test
    public void testWithdrawAppliesFee() {
        this.silverConfig.deposit(INITIAL_DEPOSIT);
        this.silverConfig.withdraw(200);
        assertEquals(INITIAL_DEPOSIT - 200 - 1, this.silverConfig.getBalance());
    }

    @Test
    public void testCannotWithdrawMoreThanAvailableIncludingFee() {
        this.silverConfig.deposit(INITIAL_DEPOSIT);
        assertThrows(IllegalStateException.class, () -> this.silverConfig.withdraw(1000));
    }

    @Test
    public void testDepositNegativeRejected() {
        assertThrows(IllegalArgumentException.class, () -> this.silverConfig.deposit(-1));
    }
}

class SilverBankAccountTest {

    private BankAccount silver;

    @BeforeEach
    void init() {
        this.silver = new SilverBankAccount(new CoreBankAccount());
    }

    @Test
    public void testSilverChargesFixedFeeOfOne() {
        this.silver.deposit(10);
        this.silver.withdraw(1);
        assertEquals(8, this.silver.getBalance());
    }

    @Test
    public void testSilverDisallowsOverdraft() {
        this.silver.deposit(0);
        assertThrows(IllegalStateException.class, () -> this.silver.withdraw(1));
    }
}

class GoldBankAccountTest {

    private BankAccount gold;

    @BeforeEach
    void init() {
        this.gold = new GoldBankAccount(new CoreBankAccount());
    }

    @Test
    public void testGoldHasNoFee() {
        this.gold.deposit(10);
        this.gold.withdraw(3);
        assertEquals(7, this.gold.getBalance());
    }

    @Test
    public void testGoldAllowsOverdraftUpToLimit() {
        this.gold.deposit(0);
        this.gold.withdraw(500);
        assertEquals(-500, this.gold.getBalance());
    }

    @Test
    public void testGoldDisallowsOverdraftBeyondLimit() {
        this.gold.deposit(0);
        assertThrows(IllegalStateException.class, () -> this.gold.withdraw(501));
    }
}

class BronzeBankAccountTest {

    private BankAccount bronze;

    @BeforeEach
    void init() {
        this.bronze = new BronzeBankAccount(new CoreBankAccount());
    }

    @Test
    public void testBronzeHasNoFeeBelowThreshold() {
        this.bronze.deposit(100);
        this.bronze.withdraw(99);
        assertEquals(1, this.bronze.getBalance());
    }

    @Test
    public void testBronzeChargesFeeAtOrAboveThreshold() {
        this.bronze.deposit(101);
        this.bronze.withdraw(100);
        assertEquals(0, this.bronze.getBalance());
    }

    @Test
    public void testBronzeDisallowsOverdraft() {
        this.bronze.deposit(0);
        assertThrows(IllegalStateException.class, () -> this.bronze.withdraw(1));
    }
}

class WithdrawalFeeTest {

    @Test
    public void testFixedWithdrawalFeeReturnsConstantValue() {
        assertEquals(3, new FixedWithdrawalFee(3).getFee(1));
    }

    @Test
    public void testThresholdWithdrawalFeeBelowThreshold() {
        assertEquals(0, new ThresholdWithdrawalFee(100, 0, 1).getFee(99));
    }

    @Test
    public void testThresholdWithdrawalFeeAtThreshold() {
        assertEquals(1, new ThresholdWithdrawalFee(100, 0, 1).getFee(100));
    }
}

class WithdrawalPolicyTest {

    @Test
    public void testStandardPolicyAllowsWhenBalanceIsEnough() {
        assertEquals(true, new StandardWithdrawalPolicy().canWithdraw(10, 10));
    }

    @Test
    public void testStandardPolicyDisallowsWhenBalanceIsNotEnough() {
        assertEquals(false, new StandardWithdrawalPolicy().canWithdraw(9, 10));
    }

    @Test
    public void testOverdraftPolicyAllowsWithinLimit() {
        assertEquals(true, new OverdraftWithdrawalPolicy(500).canWithdraw(0, 500));
    }

    @Test
    public void testOverdraftPolicyDisallowsBeyondLimit() {
        assertEquals(false, new OverdraftWithdrawalPolicy(500).canWithdraw(0, 501));
    }
}