package it.unibo.pps.e1.bankaccounts;

public interface BankAccount {

    public int getBalance();

    public void deposit(int amount);

    public void withdraw(int amount);
}
