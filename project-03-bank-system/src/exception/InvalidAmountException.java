package exception;

/**
 * EXCEPTION: InvalidAmountException
 * ===================================
 * Nem khi so tien nhap vao khong hop le (am, bang 0...).
 * extends RuntimeException -> unchecked exception
 */
public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message) {
        super(message);
    }
}