package model;

import java.time.LocalDate;

/**
 * CLASS: BorrowRecord
 * ====================
 * Ghi lai lich su muon/tra sach.
 * Moi lan muon = 1 BorrowRecord.
 */
public class BorrowRecord {

    private String    memberId;
    private String    bookId;
    private LocalDate borrowDate;
    private LocalDate returnDate; // null = chua tra

    public BorrowRecord(String memberId, String bookId) {
        this.memberId   = memberId;
        this.bookId     = bookId;
        this.borrowDate = LocalDate.now();
        this.returnDate = null;
    }

    // Constructor de doc tu CSV
    public BorrowRecord(String memberId, String bookId,
                        LocalDate borrowDate, LocalDate returnDate) {
        this.memberId   = memberId;
        this.bookId     = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public String    getMemberId()   { return memberId; }
    public String    getBookId()     { return bookId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean   isReturned()    { return returnDate != null; }

    public void markReturned() {
        this.returnDate = LocalDate.now();
    }

    // CSV format: memberId,bookId,borrowDate,returnDate
    public String toCsv() {
        return String.join(",",
                memberId, bookId,
                borrowDate.toString(),
                returnDate != null ? returnDate.toString() : "");
    }

    public static BorrowRecord fromCsv(String line) {
        String[] p = line.split(",", 4);
        LocalDate ret = (p.length == 4 && !p[3].trim().isEmpty())
                ? LocalDate.parse(p[3].trim()) : null;
        return new BorrowRecord(p[0].trim(), p[1].trim(),
                LocalDate.parse(p[2].trim()), ret);
    }

    @Override
    public String toString() {
        return String.format("ThanhVien: %-8s | Sach: %-6s | Muon: %s | Tra: %s",
                memberId, bookId, borrowDate,
                returnDate != null ? returnDate.toString() : "Chua tra");
    }
}