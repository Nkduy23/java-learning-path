package service;

import exception.AccountNotFoundException;
import exception.InvalidAmountException;
import model.Account;
import model.CheckingAccount;
import model.PremiumAccount;
import model.SavingsAccount;

import java.util.Collection;
import java.util.HashMap;

/**
 * CLASS: BankService
 * ===================
 * Xu ly toan bo nghiep vu ngan hang.
 * Luu tai khoan bang HashMap<String, Account>.
 *
 * Kien thuc:
 * - HashMap<K, V>: luu cap key-value, tra cuu O(1)
 * - Polymorphism: xu ly Account ma khong can biet loai cu the
 * - instanceof & pattern matching
 * - Custom exception: AccountNotFoundException, InvalidAmountException
 * - try/catch bat dung loai loi
 */
public class BankService {

    private HashMap<String, Account> accounts = new HashMap<>();

    // -------------------------------------------------------
    // TAO TAI KHOAN
    // -------------------------------------------------------
    public Account createChecking(String number, String owner, double balance) {
        return createAccount(new CheckingAccount(number, owner, balance));
    }

    public Account createSavings(String number, String owner, double balance, double rate) {
        return createAccount(new SavingsAccount(number, owner, balance, rate));
    }

    public Account createPremium(String number, String owner, double balance) {
        return createAccount(new PremiumAccount(number, owner, balance));
    }

    private Account createAccount(Account acc) {
        if (accounts.containsKey(acc.getAccountNumber())) {
            System.out.println("[!] So tai khoan da ton tai: " + acc.getAccountNumber());
            return null;
        }
        accounts.put(acc.getAccountNumber(), acc);
        System.out.println("[OK] Da tao tai khoan " + acc.getAccountType()
                + ": " + acc.getAccountNumber() + " - " + acc.getOwnerName());
        return acc;
    }

    // -------------------------------------------------------
    // NAP TIEN
    // throws InvalidAmountException neu so tien <= 0
    // -------------------------------------------------------
    public void deposit(String accountNumber, double amount) {
        if (amount <= 0) {
            // Dung custom exception thay vi IllegalArgumentException chung chung
            throw new InvalidAmountException("So tien nap phai lon hon 0!");
        }
        Account acc = getAccountOrThrow(accountNumber); // throws AccountNotFoundException
        try {
            acc.deposit(amount);
            System.out.println("[OK] Nap " + Account.formatMoney(amount)
                    + " vao TK " + accountNumber
                    + " | Du: " + Account.formatMoney(acc.getBalance()));
        } catch (IllegalArgumentException e) {
            throw new InvalidAmountException(e.getMessage());
        }
    }

    // -------------------------------------------------------
    // RUT TIEN
    // throws InvalidAmountException neu so tien <= 0
    // throws Exception neu vi pham rule cua loai TK
    // -------------------------------------------------------
    public void withdraw(String accountNumber, double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("So tien rut phai lon hon 0!");
        }
        Account acc = getAccountOrThrow(accountNumber);
        try {
            acc.withdraw(amount);
            System.out.println("[OK] Rut " + Account.formatMoney(amount)
                    + " tu TK " + accountNumber
                    + " | Du: " + Account.formatMoney(acc.getBalance()));
        } catch (Exception e) {
            // Giu nguyen Exception chung vi moi loai TK (Savings, Premium...)
            // co the nem loi voi noi dung khac nhau tu validateWithdraw()
            System.out.println("[LOI] " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // CHUYEN KHOAN
    // throws AccountNotFoundException neu 1 trong 2 TK khong ton tai
    // -------------------------------------------------------
    public void transfer(String fromNumber, String toNumber, double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException("So tien chuyen phai lon hon 0!");
        }
        // getAccountOrThrow tu dong throw AccountNotFoundException neu khong co
        Account from = getAccountOrThrow(fromNumber);
        Account to   = getAccountOrThrow(toNumber);

        System.out.println("\n--- CHUYEN KHOAN ---");
        System.out.println("Tu : " + fromNumber + " (" + from.getOwnerName() + ")");
        System.out.println("Den: " + toNumber   + " (" + to.getOwnerName()   + ")");
        System.out.println("So tien: " + Account.formatMoney(amount));

        try {
            from.withdraw(amount);
            to.deposit(amount);
            System.out.println("[OK] Chuyen khoan thanh cong!");
            System.out.println("    " + fromNumber + " con lai: " + Account.formatMoney(from.getBalance()));
            System.out.println("    " + toNumber   + " hien co: " + Account.formatMoney(to.getBalance()));
        } catch (Exception e) {
            System.out.println("[LOI] Chuyen khoan that bai: " + e.getMessage());
        }
    }

    // -------------------------------------------------------
    // TINH LAI SUAT
    // -------------------------------------------------------
    public void applyInterest(String accountNumber) {
        Account acc = getAccountOrThrow(accountNumber);
        if (acc instanceof SavingsAccount savings) {
            savings.applyInterest();
        } else {
            System.out.println("[!] Chi tai khoan Tiet kiem moi co lai suat!");
        }
    }

    // -------------------------------------------------------
    // XEM THONG TIN
    // throws AccountNotFoundException neu khong tim thay
    // -------------------------------------------------------
    public void printAccount(String accountNumber) {
        Account acc = getAccountOrThrow(accountNumber);
        System.out.println("\n--- THONG TIN TAI KHOAN ---");
        System.out.println("So TK    : " + acc.getAccountNumber());
        System.out.println("Chu TK   : " + acc.getOwnerName());
        System.out.println("Loai TK  : " + acc.getAccountType());
        System.out.println("So du    : " + Account.formatMoney(acc.getBalance()));
        System.out.println("Han muc  : " + Account.formatMoney(acc.getWithdrawLimit()) + "/lan");

        if (acc instanceof SavingsAccount s) {
            System.out.println("Lai suat : " + s.getInterestRate() + "%/thang");
        } else if (acc instanceof PremiumAccount p) {
            System.out.println("Overdraft: den " + Account.formatMoney(p.getOverdraftLimit()));
        }
        System.out.println();
    }

    public void printAllAccounts() {
        System.out.println("\n--- DANH SACH TAI KHOAN ---");
        System.out.println("+------------+------------+----------------------+-----------------+");
        System.out.println("| So TK      | Loai       | Chu TK               | So du           |");
        System.out.println("+------------+------------+----------------------+-----------------+");

        Collection<Account> all = accounts.values();
        if (all.isEmpty()) {
            System.out.println("  (Chua co tai khoan nao)");
        } else {
            for (Account acc : all) {
                System.out.println("| " + acc + " |");
            }
        }
        System.out.println("+------------+------------+----------------------+-----------------+\n");
    }

    public void printTransactionHistory(String accountNumber) {
        getAccountOrThrow(accountNumber).printTransactions();
    }

    // -------------------------------------------------------
    // HELPER: tra ve Account hoac throw AccountNotFoundException
    // Tap trung xu ly "khong tim thay" tai 1 cho duy nhat
    // thay vi check null o khap noi trong class
    // -------------------------------------------------------
    private Account getAccountOrThrow(String accountNumber) {
        Account acc = accounts.get(accountNumber);
        if (acc == null) {
            // Dung custom exception thay vi RuntimeException chung chung
            throw new AccountNotFoundException(accountNumber);
        }
        return acc;
    }

    public boolean hasAccount(String number) {
        return accounts.containsKey(number);
    }
}