import java.util.ArrayList;
import java.util.Scanner;

/**
 * PROJECT 02 - Student Manager
 * =============================
 * He thong quan ly sinh vien chay tren terminal.
 *
 * Kien thuc moi so voi P01:
 * - Class & Object (Student)
 * - Encapsulation (private + getter/setter)
 * - ArrayList<Student>
 * - Tach logic vao class rieng (StudentManager)
 * - Comparator, for-each
 */
public class Main {

    static Scanner sc = new Scanner(System.in);
    static StudentManager manager = new StudentManager();

    public static void main(String[] args) {
        seedData(); // nap du lieu mau de test ngay
        boolean running = true;

        printWelcome();

        while (running) {
            printMenu();
            System.out.print(">> Chon: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> doAddStudent();
                case "2" -> manager.printAll();
                case "3" -> doSearch();
                case "4" -> doSort();
                case "5" -> doDelete();
                case "6" -> doStats();
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
    // THEM sinh vien — doc tung truong tu nguoi dung
    // -------------------------------------------------------
    static void doAddStudent() {
        System.out.println("\n--- THEM SINH VIEN ---");

        System.out.print("MSSV     : ");
        String id = sc.nextLine().trim().toUpperCase();

        System.out.print("Ho ten   : ");
        String name = sc.nextLine().trim();

        double gpa = readDouble("GPA (0-10): ", 0, 10);

        System.out.print("Nganh    : ");
        String major = sc.nextLine().trim();

        // Tao object Student moi, truyen vao manager
        Student s = new Student(id, name, gpa, major);
        manager.addStudent(s);
        System.out.println();
    }

    // -------------------------------------------------------
    // TIM KIEM
    // -------------------------------------------------------
    static void doSearch() {
        System.out.println("\n--- TIM KIEM ---");
        System.out.println("1. Tim theo MSSV");
        System.out.println("2. Tim theo ten");
        System.out.println("3. Loc theo chuyen nganh");
        System.out.print(">> Chon: ");
        String opt = sc.nextLine().trim();

        switch (opt) {
            case "1" -> {
                System.out.print("Nhap MSSV: ");
                String id = sc.nextLine().trim();
                Student found = manager.findById(id);
                if (found != null) {
                    System.out.println("\nTim thay:");
                    System.out.println("  " + found);
                } else {
                    System.out.println("[!] Khong tim thay MSSV: " + id);
                }
            }
            case "2" -> {
                System.out.print("Nhap ten (co the nhap 1 phan): ");
                String kw = sc.nextLine().trim();
                ArrayList<Student> results = manager.findByName(kw);
                System.out.println("\nKet qua (" + results.size() + " sinh vien):");
                manager.printList(results);
            }
            case "3" -> {
                System.out.print("Nhap chuyen nganh: ");
                String major = sc.nextLine().trim();
                ArrayList<Student> results = manager.filterByMajor(major);
                System.out.println("\nSinh vien nganh " + major + " (" + results.size() + " nguoi):");
                manager.printList(results);
            }
            default -> System.out.println("[!] Lua chon khong hop le!");
        }
        System.out.println();
    }

    // -------------------------------------------------------
    // SAP XEP
    // -------------------------------------------------------
    static void doSort() {
        System.out.println("\n--- SAP XEP ---");
        System.out.println("1. GPA cao -> thap");
        System.out.println("2. GPA thap -> cao");
        System.out.println("3. Ten A -> Z");
        System.out.print(">> Chon: ");
        String opt = sc.nextLine().trim();

        ArrayList<Student> sorted = switch (opt) {
            case "1" -> manager.getSortedByGpa(true);
            case "2" -> manager.getSortedByGpa(false);
            case "3" -> manager.getSortedByName();
            default  -> { System.out.println("[!] Khong hop le!"); yield new ArrayList<>(); }
        };

        if (!sorted.isEmpty()) {
            System.out.println();
            manager.printList(sorted);
        }
        System.out.println();
    }

    // -------------------------------------------------------
    // XOA sinh vien
    // -------------------------------------------------------
    static void doDelete() {
        System.out.println("\n--- XOA SINH VIEN ---");
        System.out.print("Nhap MSSV can xoa: ");
        String id = sc.nextLine().trim();

        // Hien thi thong tin truoc khi xoa de xac nhan
        Student found = manager.findById(id);
        if (found != null) {
            System.out.println("Sinh vien: " + found);
            System.out.print("Xac nhan xoa? (y/n): ");
            String confirm = sc.nextLine().trim();
            if (confirm.equalsIgnoreCase("y")) {
                manager.removeStudent(id);
            } else {
                System.out.println("Da huy.");
            }
        } else {
            System.out.println("[!] Khong tim thay MSSV: " + id);
        }
        System.out.println();
    }

    // -------------------------------------------------------
    // THONG KE
    // -------------------------------------------------------
    static void doStats() {
        System.out.println("\n--- THONG KE ---");
        System.out.println("Tong so sinh vien : " + manager.getTotalStudents());
        System.out.printf("GPA trung binh    : %.2f%n", manager.getAverageGpa());

        Student top = manager.getTopStudent();
        if (top != null) {
            System.out.println("Sinh vien xuat sac: " + top.getName() + " (GPA: " + top.getGpa() + ")");
        }
        System.out.println();
    }

    // -------------------------------------------------------
    // HELPER: doc so thuc co kiem tra khoang hop le
    // -------------------------------------------------------
    static double readDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                if (val < min || val > max) {
                    System.out.println("[!] Gia tri phai tu " + min + " den " + max);
                } else {
                    return val;
                }
            } catch (NumberFormatException e) {
                System.out.println("[!] Vui long nhap so!");
            }
        }
    }

    // -------------------------------------------------------
    // DU LIEU MAU — de chay la co the test ngay
    // -------------------------------------------------------
    static void seedData() {
        manager.addStudent(new Student("SV001", "Nguyen Van An",    8.5, "CNTT"));
        manager.addStudent(new Student("SV002", "Tran Thi Bich",    9.2, "CNTT"));
        manager.addStudent(new Student("SV003", "Le Hoang Nam",     6.8, "Kinh te"));
        manager.addStudent(new Student("SV004", "Pham Minh Duc",    7.4, "Co khi"));
        manager.addStudent(new Student("SV005", "Hoang Thi Lan",    5.5, "Kinh te"));
        manager.addStudent(new Student("SV006", "Vo Thanh Tung",    9.8, "CNTT"));
        manager.addStudent(new Student("SV007", "Dang Ngoc Huong",  4.2, "Co khi"));
    }

    static void printWelcome() {
        System.out.println("==============================");
        System.out.println("   Student Manager v1.0");
        System.out.println("   Project 02 / 05");
        System.out.println("==============================\n");
    }

    static void printMenu() {
        System.out.println("+------------------------------+");
        System.out.println("|  1. Them sinh vien           |");
        System.out.println("|  2. Xem danh sach            |");
        System.out.println("|  3. Tim kiem / Loc           |");
        System.out.println("|  4. Sap xep                  |");
        System.out.println("|  5. Xoa sinh vien            |");
        System.out.println("|  6. Thong ke                 |");
        System.out.println("|  0. Thoat                    |");
        System.out.println("+------------------------------+");
    }
}