import java.util.ArrayList;
import java.util.Scanner;

/**
 * PROJECT 01 - Calculator CLI
 * ============================
 * May tinh dong lenh don gian.
 * Kien thuc: bien, kieu du lieu, if/else, switch, vong lap, method, ArrayList
 */
public class Calculator {

    // ArrayList luu lich su cac phep tinh trong session
    private static ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        printWelcome();

        while (running) {
            printMenu();

            System.out.print(">> Chon: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1" -> doCalculation(sc);
                case "2" -> printHistory();
                case "3" -> clearHistory();
                case "0" -> {
                    System.out.println("\nTam biet! Da luu " + history.size() + " phep tinh.");
                    running = false;
                }
                default -> System.out.println("[!] Lua chon khong hop le, thu lai!\n");
            }
        }

        sc.close();
    }

    // Thuc hien 1 phep tinh
    private static void doCalculation(Scanner sc) {
        System.out.println("\n-----------------------------");

        double a = readNumber(sc, "Nhap so thu nhat: ");

        System.out.println("Chon phep tinh: + | - | * | / | %");
        System.out.print("Toan tu: ");
        String operator = sc.nextLine().trim();

        double b = readNumber(sc, "Nhap so thu hai : ");

        double result = calculate(a, operator, b);

        if (!Double.isNaN(result)) {
            String record = formatResult(a, operator, b, result);
            System.out.println("[OK] Ket qua: " + record);
            history.add(record);
        }

        System.out.println("-----------------------------\n");
    }

    // Thuc hien phep tinh, tra ve Double.NaN neu loi
    private static double calculate(double a, String operator, double b) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> {
                if (b == 0) {
                    System.out.println("[LOI] Khong the chia cho 0!");
                    yield Double.NaN;
                }
                yield a / b;
            }
            case "%" -> {
                if (b == 0) {
                    System.out.println("[LOI] Khong the chia du cho 0!");
                    yield Double.NaN;
                }
                yield a % b;
            }
            default -> {
                System.out.println("[LOI] Toan tu '" + operator + "' khong hop le!");
                yield Double.NaN;
            }
        };
    }

    // Doc so tu nguoi dung, retry neu nhap sai
    private static double readNumber(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = sc.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("[!] '" + input + "' khong phai so. Nhap lai!");
            }
        }
    }

    // Format ket qua
    private static String formatResult(double a, String op, double b, double result) {
        return formatNum(a) + " " + op + " " + formatNum(b) + " = " + formatNum(result);
    }

    // Hien thi so nguyen khong co .0
    private static String formatNum(double n) {
        if (n == Math.floor(n) && !Double.isInfinite(n)) {
            return String.valueOf((long) n);
        }
        return String.format("%.6f", n).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    // In lich su
    private static void printHistory() {
        System.out.println("\n=== LICH SU PHEP TINH ===");
        if (history.isEmpty()) {
            System.out.println("  (Chua co phep tinh nao)");
        } else {
            for (int i = 0; i < history.size(); i++) {
                System.out.printf("  %2d. %s%n", i + 1, history.get(i));
            }
        }
        System.out.println("=========================\n");
    }

    // Xoa lich su
    private static void clearHistory() {
        history.clear();
        System.out.println("[OK] Da xoa lich su!\n");
    }

    // Man hinh chao mung
    private static void printWelcome() {
        System.out.println("==============================");
        System.out.println("   Java Calculator CLI");
        System.out.println("   Project 01 / 05");
        System.out.println("==============================");
        System.out.println();
    }

    // Menu chinh
    private static void printMenu() {
        System.out.println("+------------------------------+");
        System.out.println("|  1. Tinh toan                |");
        System.out.println("|  2. Xem lich su              |");
        System.out.println("|  3. Xoa lich su              |");
        System.out.println("|  0. Thoat                    |");
        System.out.println("+------------------------------+");
    }
}