package model;

/**
 * CLASS: PremiumAccount (Tai khoan VIP)
 * =======================================
 * Han muc rut cao 200 trieu/lan. Co the rut qua so du (overdraft)
 * toi da 10 trieu (vay ngan han).
 *
 * Kien thuc:
 * - Them field rieng: overdraftLimit
 * - Override validateWithdraw() voi logic phuc tap hon
 * - instanceof se dung trong BankService de phan biet loai TK
 */
public class PremiumAccount extends Account {

    private static final double WITHDRAW_LIMIT   = 200_000_000; // 200 trieu
    private static final double OVERDRAFT_LIMIT  = 10_000_000;  // rut vuot 10 trieu

    public PremiumAccount(String accountNumber, String ownerName, double initialBalance) {
        super(accountNumber, ownerName, initialBalance);
    }

    // -------------------------------------------------------
    // OVERRIDE: cho phep rut qua so du (den -10 trieu)
    // -------------------------------------------------------
    @Override
    protected void validateWithdraw(double amount) throws Exception {
        if (amount <= 0) throw new IllegalArgumentException("So tien rut phai lon hon 0!");
        if (amount > WITHDRAW_LIMIT) {
            throw new Exception("Vuot han muc rut tien VIP! Gioi han: " + formatMoney(WITHDRAW_LIMIT));
        }
        if (balance - amount < -OVERDRAFT_LIMIT) {
            throw new Exception("Vuot han muc overdraft! Co the rut them toi da: "
                    + formatMoney(balance + OVERDRAFT_LIMIT));
        }
    }

    @Override
    public String getAccountType() { return "VIP"; }

    @Override
    public double getWithdrawLimit() { return WITHDRAW_LIMIT; }

    public double getOverdraftLimit() { return OVERDRAFT_LIMIT; }
}