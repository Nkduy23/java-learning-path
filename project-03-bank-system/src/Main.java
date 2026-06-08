import service.BankService;

import java.util.Scanner;

/**
 * PROJECT 03 - Bank System
 * =========================
 * He thong ngan hang don gian voi 3 loai tai khoan.
 *
 * Kien thuc moi so voi P02:
 * - abstract class & inheritance (Account -> 3 loai TK)
 * - abstract method & override
 * - protected field
 * - instanceof & pattern matching
 * - try/catch/finally & custom exception
 * - HashMap<String, Account>
 * - package: tach code vao thu muc theo chuc nang
 */
public class Main {

    static Scanner sc      = new Scanner(System.in);
    static BankService bank = new BankService();

    public static void main(String[] args) {
        seedData();
        boolean running = true;

        printWelcome();

        while (running) {
            printMenu();
            System.out.print(">> Chon: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> doCreateAccount();
                case "2" -> bank.printAllAccounts();
                case "3" -> doViewAccount();
                case "4" -> doDeposit();
                case "5" -> doWithdraw();
                case "6" -> doTransfer();
                case "7" -> doApplyInterest();
                case "8" -> doViewHistory();
                case "0" -> {
                    System.out.println("\nTam biet!\n");
                    running = false;
                }
                default -> System.out.println("[!] Lua chon khong hop le!\n");
            }
        }
        sc.close();
    }

    // -------------------------------------------------------
    // TAO TAI KHOAN
    // -------------------------------------------------------
    static void doCreateAccount() {
        System.out.println("\n--- TAO TAI KHOAN ---");
        System.out.println("1. Tai khoan Thanh toan (han muc rut 50tr/lan)");
        System.out.println("2. Tai khoan Tiet kiem (co lai suat, han muc 20tr/lan)");
        System.out.println("3. Tai khoan VIP       (han muc 200tr/lan, ho tro overdraft)");
        System.out.print(">> Loai TK: ");
        String type = sc.nextLine().trim();

        System.out.print("So tai khoan: ");
        String number = sc.nextLine().trim().toUpperCase();

        System.out.print("Chu tai khoan: ");
        String owner = sc.nextLine().trim();

        double balance = readDouble("So du ban dau (VND): ", 0, Double.MAX_VALUE);

        switch (type) {
            case "1" -> bank.createChecking(number, owner, balance);
            case "2" -> {
                double rate = readDouble("Lai suat (%/thang): ", 0, 100);
                bank.createSavings(number, owner, balance, rate);
            }
            case "3" -> bank.createPremium(number, owner, balance);
            default  -> System.out.println("[!] Loai tai khoan khong hop le!");
        }
        System.out.println();
    }

    static void doViewAccount() {
        System.out.print("\nNhap so tai khoan: ");
        String number = sc.nextLine().trim().toUpperCase();
        try {
            bank.printAccount(number);
        } catch (RuntimeException e) {
            System.out.println("[LOI] " + e.getMessage() + "\n");
        }
    }

    static void doDeposit() {
        System.out.println("\n--- NAP TIEN ---");
        System.out.print("So tai khoan: ");
        String number = sc.nextLine().trim().toUpperCase();
        double amount = readDouble("So tien nap (VND): ", 1, Double.MAX_VALUE);
        try {
            bank.deposit(number, amount);
        } catch (RuntimeException e) {
            System.out.println("[LOI] " + e.getMessage());
        }
        System.out.println();
    }

    static void doWithdraw() {
        System.out.println("\n--- RUT TIEN ---");
        System.out.print("So tai khoan: ");
        String number = sc.nextLine().trim().toUpperCase();
        double amount = readDouble("So tien rut (VND): ", 1, Double.MAX_VALUE);
        try {
            bank.withdraw(number, amount);
        } catch (RuntimeException e) {
            System.out.println("[LOI] " + e.getMessage());
        }
        System.out.println();
    }

    static void doTransfer() {
        System.out.println("\n--- CHUYEN KHOAN ---");
        System.out.print("Tu tai khoan  : ");
        String from = sc.nextLine().trim().toUpperCase();
        System.out.print("Den tai khoan : ");
        String to = sc.nextLine().trim().toUpperCase();
        double amount = readDouble("So tien (VND): ", 1, Double.MAX_VALUE);
        try {
            bank.transfer(from, to, amount);
        } catch (RuntimeException e) {
            System.out.println("[LOI] " + e.getMessage());
        }
        System.out.println();
    }

    static void doApplyInterest() {
        System.out.print("\nNhap so TK tiet kiem: ");
        String number = sc.nextLine().trim().toUpperCase();
        try {
            bank.applyInterest(number);
        } catch (RuntimeException e) {
            System.out.println("[LOI] " + e.getMessage());
        }
        System.out.println();
    }

    static void doViewHistory() {
        System.out.print("\nNhap so tai khoan: ");
        String number = sc.nextLine().trim().toUpperCase();
        try {
            bank.printTransactionHistory(number);
        } catch (RuntimeException e) {
            System.out.println("[LOI] " + e.getMessage() + "\n");
        }
    }

    // -------------------------------------------------------
    // DU LIEU MAU
    // -------------------------------------------------------
    static void seedData() {
        bank.createChecking("TT001", "Nguyen Van An",   5_000_000);
        bank.createSavings ("TK001", "Tran Thi Bich",  50_000_000, 0.7);
        bank.createPremium ("VIP001","Le Hoang Nam",   200_000_000);
        bank.deposit("TT001", 2_000_000);
        bank.deposit("TK001", 10_000_000);
        System.out.println();
    }

    // -------------------------------------------------------
    // HELPERS
    // -------------------------------------------------------
    static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim().replace(",", ""));
                if (val < min || val > max) {
                    System.out.println("[!] Gia tri khong hop le!");
                } else {
                    return val;
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Vui long nhap so!");
            }
        }
    }

    static void printWelcome() {
        System.out.println("==============================");
        System.out.println("   Java Bank System v1.0");
        System.out.println("   Project 03 / 05");
        System.out.println("==============================\n");
    }

    static void printMenu() {
        System.out.println("+------------------------------+");
        System.out.println("|  1. Tao tai khoan            |");
        System.out.println("|  2. Danh sach tai khoan      |");
        System.out.println("|  3. Xem chi tiet tai khoan   |");
        System.out.println("|  4. Nap tien                 |");
        System.out.println("|  5. Rut tien                 |");
        System.out.println("|  6. Chuyen khoan             |");
        System.out.println("|  7. Tinh lai suat (tiet kiem)|");
        System.out.println("|  8. Lich su giao dich        |");
        System.out.println("|  0. Thoat                    |");
        System.out.println("+------------------------------+");
    }
}