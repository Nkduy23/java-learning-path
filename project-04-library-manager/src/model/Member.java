package model;

import java.util.ArrayList;

/**
 * CLASS: Member
 * ==============
 * Thanh vien thu vien. Co the muon toi da 3 sach cung luc.
 */
public class Member {

    private String id;
    private String name;
    private String email;
    private ArrayList<String> borrowedBookIds = new ArrayList<>(); // danh sach ID sach dang muon

    private static final int MAX_BORROW = 3;

    public Member(String id, String name, String email) {
        this.id    = id;
        this.name  = name;
        this.email = email;
    }

    // -------------------------------------------------------
    // GETTERS
    // -------------------------------------------------------
    public String            getId()             { return id; }
    public String            getName()           { return name; }
    public String            getEmail()          { return email; }
    public ArrayList<String> getBorrowedBookIds(){ return borrowedBookIds; }
    public int               getBorrowCount()    { return borrowedBookIds.size(); }

    // -------------------------------------------------------
    // MUON / TRA SACH
    // -------------------------------------------------------
    public boolean borrowBook(String bookId) {
        if (borrowedBookIds.size() >= MAX_BORROW) {
            System.out.println("[!] " + name + " da muon toi da " + MAX_BORROW + " sach!");
            return false;
        }
        if (borrowedBookIds.contains(bookId)) {
            System.out.println("[!] Thanh vien nay da muon quyen sach nay roi!");
            return false;
        }
        borrowedBookIds.add(bookId);
        return true;
    }

    public boolean returnBook(String bookId) {
        return borrowedBookIds.remove(bookId);
    }

    public boolean hasBorrowed(String bookId) {
        return borrowedBookIds.contains(bookId);
    }

    // -------------------------------------------------------
    // CSV
    // -------------------------------------------------------
    public String toCsv() {
        String borrowed = String.join("|", borrowedBookIds); // dung | vi , la delimiter CSV
        return String.join(",", id, name, email, borrowed);
    }

    public static Member fromCsv(String line) {
        String[] parts = line.split(",", 4);
        Member m = new Member(parts[0].trim(), parts[1].trim(), parts[2].trim());
        if (parts.length == 4 && !parts[3].trim().isEmpty()) {
            for (String bookId : parts[3].trim().split("\\|")) {
                m.borrowedBookIds.add(bookId.trim());
            }
        }
        return m;
    }

    @Override
    public String toString() {
        return String.format("%-8s | %-20s | %-25s | Dang muon: %d/%d",
                id, name, email, borrowedBookIds.size(), MAX_BORROW);
    }
}