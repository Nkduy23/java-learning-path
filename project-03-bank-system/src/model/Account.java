package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * ABSTRACT CLASS: Account
 * ========================
 * Lop co so cho moi loai tai khoan ngan hang.
 *
 * Kien thuc:
 * - abstract class: khong the tao truc tiep (new Account() -> LOI)
 * - abstract method: cac lop con BUOC PHAI override
 * - protected: field chi truy cap duoc tu lop con
 * - LocalDateTime: lam viec voi ngay gio
 */
public abstract class Account {

    // protected: subclass co the truy cap, ben ngoai package khong duoc
    protected String accountNumber;
    protected String ownerName;
    protected double balance;
    protected LocalDateTime createdAt;
    protected ArrayList<String> transactions = new ArrayList<>();

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    // -------------------------------------------------------
    // CONSTRUCTOR
    // -------------------------------------------------------
    public Account(String accountNumber, String ownerName, double initialBalance) {
        this.accountNumber = accountNumber;
        this.ownerName     = ownerName;
        this.balance       = initialBalance;
        this.createdAt     = LocalDateTime.now();
        logTransaction("Mo tai khoan voi so du ban dau: " + formatMoney(initialBalance));
    }

    // -------------------------------------------------------
    // ABSTRACT METHODS — lop con BUOC PHAI implement
    // Moi loai tai khoan co quy tac rut tien khac nhau
    // -------------------------------------------------------
    public abstract String getAccountType();
    public abstract double getWithdrawLimit();

    // -------------------------------------------------------
    // CONCRETE METHODS — dung chung cho moi loai tai khoan
    // -------------------------------------------------------
    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("So tien nap phai lon hon 0!");
        }
        balance += amount;
        logTransaction("Nap tien: +" + formatMoney(amount) + " | Du: " + formatMoney(balance));
    }

    // withdraw la abstract o muc logic validation
    // nhung logic tru tien la chung -> dung Template Method pattern nhe
    public void withdraw(double amount) throws Exception {
        validateWithdraw(amount); // moi loai TK co the override cai nay
        balance -= amount;
        logTransaction("Rut tien: -" + formatMoney(amount) + " | Du: " + formatMoney(balance));
    }

    // Hook method: lop con co the override de them rule rieng
    protected void validateWithdraw(double amount) throws Exception {
        if (amount <= 0) throw new IllegalArgumentException("So tien rut phai lon hon 0!");
        if (amount > balance) throw new Exception("So du khong du! Hien co: " + formatMoney(balance));
        if (amount > getWithdrawLimit()) {
            throw new Exception("Vuot han muc rut tien! Gioi han: " + formatMoney(getWithdrawLimit()));
        }
    }

    // -------------------------------------------------------
    // GETTERS
    // -------------------------------------------------------
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName()     { return ownerName; }
    public double getBalance()       { return balance; }

    // -------------------------------------------------------
    // LICH SU GIAO DICH
    // -------------------------------------------------------
    protected void logTransaction(String note) {
        String time = LocalDateTime.now().format(FORMATTER);
        transactions.add("[" + time + "] " + note);
    }

    public void printTransactions() {
        System.out.println("\n--- LICH SU GIAO DICH: " + accountNumber + " ---");
        if (transactions.isEmpty()) {
            System.out.println("  (Chua co giao dich nao)");
        } else {
            for (String t : transactions) {
                System.out.println("  " + t);
            }
        }
        System.out.println();
    }

    // -------------------------------------------------------
    // FORMAT tien VND
    // -------------------------------------------------------
    public static String formatMoney(double amount) {
        return String.format("%,.0f VND", amount);
    }

    // -------------------------------------------------------
    // toString — in thong tin tai khoan
    // -------------------------------------------------------
    @Override
    public String toString() {
        return String.format("%-12s | %-8s | %-20s | %15s",
                accountNumber, getAccountType(), ownerName, formatMoney(balance));
    }
}