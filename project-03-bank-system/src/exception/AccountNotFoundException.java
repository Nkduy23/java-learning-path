package exception;

/**
 * EXCEPTION: AccountNotFoundException
 * =====================================
 * Nem khi khong tim thay tai khoan theo so TK.
 * extends RuntimeException -> unchecked exception:
 * khong can khai bao throws, khong bat cung duoc
 */
public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountNumber) {
        super("Khong tim thay tai khoan: " + accountNumber);
    }
}