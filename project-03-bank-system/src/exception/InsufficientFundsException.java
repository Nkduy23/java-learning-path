package exception;

/**
 * EXCEPTION: InsufficientFundsException
 * =======================================
 * Nem khi so du khong du de thuc hien giao dich.
 * extends Exception -> checked exception:
 * method nao nem phai khai bao "throws InsufficientFundsException"
 */
public class InsufficientFundsException extends Exception {
    private double shortfall; // con thieu bao nhieu

    public InsufficientFundsException(double shortfall) {
        super("So du khong du! Con thieu: " + String.format("%,.0f VND", shortfall));
        this.shortfall = shortfall;
    }

    public double getShortfall() { return shortfall; }
}