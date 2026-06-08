package model;

/**
 * CLASS: CheckingAccount (Tai khoan thanh toan)
 * ===============================================
 * Tai khoan co ban, han muc rut 50 trieu/lan.
 *
 * Kien thuc:
 * - extends: ke thua tu Account
 * - super(): goi constructor lop cha
 * - @Override: ghi de method abstract
 */
public class CheckingAccount extends Account {

    private static final double WITHDRAW_LIMIT = 50_000_000; // 50 trieu

    public CheckingAccount(String accountNumber, String ownerName, double initialBalance) {
        super(accountNumber, ownerName, initialBalance); // goi constructor Account
    }

    @Override
    public String getAccountType() {
        return "Thanh toan";
    }

    @Override
    public double getWithdrawLimit() {
        return WITHDRAW_LIMIT;
    }
}