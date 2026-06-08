import service.LibraryService;
import model.Book;
import model.Member;
import util.DataSeeder;

import java.util.List;
import java.util.Scanner;

/**
 * PROJECT 04 - Library Manager
 * =============================
 * He thong quan ly thu vien — du lieu luu vao file CSV,
 * khong mat khi tat chuong trinh.
 *
 * Kien thuc moi so voi P03:
 * - Stream API: filter, map, sorted, collect, count, groupingBy
 * - Optional<T>: xu ly null an toan
 * - Lambda & Method Reference
 * - File I/O: BufferedReader / BufferedWriter
 * - try-with-resources
 * - Builder Pattern (Book.builder()...)
 * - Generic method <T>
 */
public class Main {

    static Scanner        sc      = new Scanner(System.in);
    static LibraryService library;

    // Thu muc chua file du lieu — duong dan tuong doi
    static final String DATA_DIR = "data";

    public static void main(String[] args) {
        library = new LibraryService(DATA_DIR);

        // Chi seed neu chua co du lieu (file trong)
        if (args.length > 0 && args[0].equals("--seed")) {
            DataSeeder.seed(library);
        }

        boolean running = true;
        printWelcome();

        while (running) {
            printMenu();
            System.out.print(">> Chon: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1"  -> doBookMenu();
                case "2"  -> doMemberMenu();
                case "3"  -> doBorrow();
                case "4"  -> doReturn();
                case "5"  -> library.printAllRecords();
                case "6"  -> library.printStats();
                case "0"  -> { System.out.println("\nTam biet!\n"); running = false; }
                default   -> System.out.println("[!] Lua chon khong hop le!\n");
            }
        }
        sc.close();
    }

    // -------------------------------------------------------
    // MENU SACH
    // -------------------------------------------------------
    static void doBookMenu() {
        System.out.println("\n=== QUAN LY SACH ===");
        System.out.println("1. Xem tat ca sach");
        System.out.println("2. Sach dang co san");
        System.out.println("3. Tim kiem sach");
        System.out.println("4. Loc theo the loai");
        System.out.println("5. Sap xep theo nam");
        System.out.println("6. Them sach moi");
        System.out.println("7. Xoa sach");
        System.out.print(">> Chon: ");
        String opt = sc.nextLine().trim();

        switch (opt) {
            case "1" -> library.printAllBooks();
            case "2" -> {
                List<Book> avail = library.getAvailableBooks();
                System.out.println("\nSach co san (" + avail.size() + "):");
                library.printBooks(avail);
            }
            case "3" -> {
                System.out.print("Tu khoa (tieu de / tac gia): ");
                String kw = sc.nextLine().trim();
                List<Book> results = library.searchBooks(kw);
                System.out.println("\nKet qua (" + results.size() + "):");
                library.printBooks(results);
            }
            case "4" -> {
                System.out.print("The loai: ");
                String genre = sc.nextLine().trim();
                library.printBooks(library.filterByGenre(genre));
            }
            case "5" -> {
                System.out.print("1. Moi nhat truoc  2. Cu nhat truoc: ");
                boolean desc = sc.nextLine().trim().equals("1");
                library.printBooks(library.getSortedByYear(desc));
            }
            case "6" -> doAddBook();
            case "7" -> {
                System.out.print("Ma sach can xoa: ");
                library.removeBook(sc.nextLine().trim().toUpperCase());
            }
            default -> System.out.println("[!] Khong hop le!");
        }
        System.out.println();
    }

    static void doAddBook() {
        System.out.println("\n--- THEM SACH ---");
        System.out.print("Ma sach (vd: B008): "); String id    = sc.nextLine().trim().toUpperCase();
        System.out.print("Tieu de           : "); String title  = sc.nextLine().trim();
        System.out.print("Tac gia           : "); String author = sc.nextLine().trim();
        System.out.print("The loai          : "); String genre  = sc.nextLine().trim();
        int year = (int) readDouble("Nam xuat ban     : ", 1000, 2100);

        Book book = Book.builder()
                .id(id).title(title).author(author)
                .genre(genre).publishYear(year).available(true)
                .build();
        library.addBook(book);
    }

    // -------------------------------------------------------
    // MENU THANH VIEN
    // -------------------------------------------------------
    static void doMemberMenu() {
        System.out.println("\n=== QUAN LY THANH VIEN ===");
        System.out.println("1. Xem tat ca thanh vien");
        System.out.println("2. Tim kiem thanh vien");
        System.out.println("3. Lich su muon cua thanh vien");
        System.out.println("4. Them thanh vien moi");
        System.out.print(">> Chon: ");
        String opt = sc.nextLine().trim();

        switch (opt) {
            case "1" -> library.printAllMembers();
            case "2" -> {
                System.out.print("Tu khoa (ten / email): ");
                library.printMembers(library.searchMembers(sc.nextLine().trim()));
            }
            case "3" -> {
                System.out.print("Ma thanh vien: ");
                library.printBorrowHistory(sc.nextLine().trim().toUpperCase());
            }
            case "4" -> {
                System.out.println("\n--- THEM THANH VIEN ---");
                System.out.print("Ma TV (vd: M004): "); String id    = sc.nextLine().trim().toUpperCase();
                System.out.print("Ho ten          : "); String name  = sc.nextLine().trim();
                System.out.print("Email           : "); String email = sc.nextLine().trim();
                library.addMember(new Member(id, name, email));
            }
            default -> System.out.println("[!] Khong hop le!");
        }
        System.out.println();
    }

    // -------------------------------------------------------
    // MUON / TRA SACH
    // -------------------------------------------------------
    static void doBorrow() {
        System.out.println("\n--- MUON SACH ---");
        System.out.print("Ma thanh vien: ");
        String memberId = sc.nextLine().trim().toUpperCase();
        System.out.print("Ma sach      : ");
        String bookId = sc.nextLine().trim().toUpperCase();
        library.borrowBook(memberId, bookId);
        System.out.println();
    }

    static void doReturn() {
        System.out.println("\n--- TRA SACH ---");
        System.out.print("Ma thanh vien: ");
        String memberId = sc.nextLine().trim().toUpperCase();
        System.out.print("Ma sach      : ");
        String bookId = sc.nextLine().trim().toUpperCase();
        library.returnBook(memberId, bookId);
        System.out.println();
    }

    // -------------------------------------------------------
    // HELPERS
    // -------------------------------------------------------
    static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                if (val >= min && val <= max) return val;
                System.out.println("[!] Gia tri phai tu " + (int)min + " den " + (int)max);
            } catch (NumberFormatException e) {
                System.out.println("[!] Vui long nhap so!");
            }
        }
    }

    static void printWelcome() {
        System.out.println("==============================");
        System.out.println("   Library Manager v1.0");
        System.out.println("   Project 04 / 05");
        System.out.println("==============================\n");
    }

    static void printMenu() {
        System.out.println("+------------------------------+");
        System.out.println("|  1. Quan ly sach             |");
        System.out.println("|  2. Quan ly thanh vien       |");
        System.out.println("|  3. Muon sach                |");
        System.out.println("|  4. Tra sach                 |");
        System.out.println("|  5. Lich su muon/tra         |");
        System.out.println("|  6. Thong ke                 |");
        System.out.println("|  0. Thoat                    |");
        System.out.println("+------------------------------+");
    }
}