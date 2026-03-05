package it.unibo.pps.e1.withdrawalpolicies;

public interface WithdrawalPolicy {

    /*
        * Verifica se è possibile effettuare un prelievo, dato il saldo attuale e l'importo totale da addebitare (compreso di eventuali commissioni).
        * @param currentBalance il saldo attuale del conto
        * @param totalDebit l'importo totale da addebitare (compreso di eventuali commissioni)
        * @return true se è possibile effettuare il prelievo, false altrimenti
     */
    boolean canWithdraw(int currentBalance, int totalDebit);
}