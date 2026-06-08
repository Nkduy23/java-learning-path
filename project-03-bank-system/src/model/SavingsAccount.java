package model;

/**
 * CLASS: SavingsAccount (Tai khoan tiet kiem)
 * =============================================
 * Co lai suat hang thang. Han muc rut 20 trieu/lan.
 * Them quy tac: phai giu lai it nhat 1 trieu trong TK.
 *
 * Kien thuc:
 * - Override validateWithdraw() de them rule rieng
 * - Them field rieng (interestRate) ngoai field cua cha
 */
public class SavingsAccount extends Account {

    private static final double WITHDRAW_LIMIT   = 20_000_000; // 20 trieu
    private static final double MIN_BALANCE      = 1_000_000;  // so du toi thieu
    private double interestRate; // lai suat thang (%)

    public SavingsAccount(String accountNumber, String ownerName,
                          double initialBalance, double interestRate) {
        super(accountNumber, ownerName, initialBalance);
        this.interestRate = interestRate;
    }

    // -------------------------------------------------------
    // TINH LAI SUAT — them tien vao TK
    // -------------------------------------------------------
    public void applyInterest() {
        double interest = balance * (interestRate / 100);
        balance += interest;
        logTransaction("Lai suat " + interestRate + "%/thang: +" + formatMoney(interest)
                + " | Du: " + formatMoney(balance));
        System.out.println("[OK] Lai suat: +" + formatMoney(interest));
    }

    // -------------------------------------------------------
    // OVERRIDE validateWithdraw — them rule: phai giu lai 1 trieu
    // Goi super.validateWithdraw() truoc de tan dung logic chung
    // -------------------------------------------------------
    @Override
    protected void validateWithdraw(double amount) throws Exception {
        super.validateWithdraw(amount); // kiem tra > 0, du so du, han muc
        if (balance - amount < MIN_BALANCE) {
            throw new Exception("Tai khoan tiet kiem phai giu lai toi thieu "
                    + formatMoney(MIN_BALANCE) + "!");
        }
    }

    @Override
    public String getAccountType() { return "Tiet kiem"; }

    @Override
    public double getWithdrawLimit() { return WITHDRAW_LIMIT; }

    public double getInterestRate() { return interestRate; }
}