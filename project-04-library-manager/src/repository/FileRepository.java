package repository;

import model.Book;
import model.BorrowRecord;
import model.Member;

import java.io.*;
import java.util.ArrayList;

/**
 * CLASS: FileRepository
 * ======================
 * Doc va ghi du lieu ra file CSV.
 * Tach biet hoan toan khoi logic nghiep vu.
 *
 * Kien thuc:
 * - File I/O: FileReader, FileWriter, BufferedReader, BufferedWriter
 * - try-with-resources: tu dong dong file du co loi hay khong
 * - ArrayList la kieu tra ve, khong phu thuoc vao implementation cu the
 */
public class FileRepository {

    private final String booksFile;
    private final String membersFile;
    private final String recordsFile;

    public FileRepository(String dataDir) {
        this.booksFile   = dataDir + "/books.csv";
        this.membersFile = dataDir + "/members.csv";
        this.recordsFile = dataDir + "/records.csv";
    }

    // -------------------------------------------------------
    // BOOKS
    // -------------------------------------------------------
    public ArrayList<Book> loadBooks() {
        return loadLines(booksFile, Book::fromCsv); // method reference
    }

    public void saveBooks(ArrayList<Book> books) {
        saveLines(booksFile, books, Book::toCsv);
    }

    // -------------------------------------------------------
    // MEMBERS
    // -------------------------------------------------------
    public ArrayList<Member> loadMembers() {
        return loadLines(membersFile, Member::fromCsv);
    }

    public void saveMembers(ArrayList<Member> members) {
        saveLines(membersFile, members, Member::toCsv);
    }

    // -------------------------------------------------------
    // BORROW RECORDS
    // -------------------------------------------------------
    public ArrayList<BorrowRecord> loadRecords() {
        return loadLines(recordsFile, BorrowRecord::fromCsv);
    }

    public void saveRecords(ArrayList<BorrowRecord> records) {
        saveLines(recordsFile, records, BorrowRecord::toCsv);
    }

    // -------------------------------------------------------
    // GENERIC HELPER — doc file, chuyen tung dong thanh object
    //
    // <T>: Generic type — dung duoc cho Book, Member, BorrowRecord
    // java.util.function.Function<String, T>: nhan String tra ve T
    // -------------------------------------------------------
    private <T> ArrayList<T> loadLines(String filePath,
                                       java.util.function.Function<String, T> parser) {
        ArrayList<T> list = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return list; // file chua ton tai -> tra ve list rong

        // try-with-resources: tu dong goi br.close() khi thoat khoi block
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    list.add(parser.apply(line)); // goi fromCsv() tuong ung
                }
            }
        } catch (IOException e) {
            System.out.println("[!] Loi doc file " + filePath + ": " + e.getMessage());
        }
        return list;
    }

    // <T>: Generic type
    // java.util.function.Function<T, String>: nhan T tra ve String (toCsv)
    private <T> void saveLines(String filePath, ArrayList<T> list,
                               java.util.function.Function<T, String> serializer) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (T item : list) {
                bw.write(serializer.apply(item)); // goi toCsv() tuong ung
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[!] Loi ghi file " + filePath + ": " + e.getMessage());
        }
    }
}