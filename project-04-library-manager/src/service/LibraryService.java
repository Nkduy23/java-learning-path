package service;

import model.Book;
import model.BorrowRecord;
import model.Member;
import repository.FileRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CLASS: LibraryService
 * ======================
 * Xu ly toan bo nghiep vu thu vien.
 * Load du lieu tu file luc khoi dong, luu lai khi co thay doi.
 *
 * Kien thuc:
 * - Stream API: filter, map, sorted, collect, count, min, max
 * - Optional<T>: xu ly ket qua co the null an toan
 * - Lambda expression: (x) -> x.isAvailable()
 * - Method reference: Book::getTitle
 * - Collectors: toList(), groupingBy(), counting()
 */
public class LibraryService {

    private ArrayList<Book>         books;
    private ArrayList<Member>       members;
    private ArrayList<BorrowRecord> records;
    private FileRepository          repo;

    public LibraryService(String dataDir) {
        this.repo    = new FileRepository(dataDir);
        this.books   = repo.loadBooks();
        this.members = repo.loadMembers();
        this.records = repo.loadRecords();
        System.out.println("[OK] Da tai: " + books.size() + " sach, "
                + members.size() + " thanh vien, "
                + records.size() + " lich su.");
    }

    // -------------------------------------------------------
    // SACH — CRUD
    // -------------------------------------------------------
    public void addBook(Book book) {
        if (findBookById(book.getId()).isPresent()) {
            System.out.println("[!] Ma sach da ton tai: " + book.getId());
            return;
        }
        books.add(book);
        repo.saveBooks(books);
        System.out.println("[OK] Da them sach: " + book.getTitle());
    }

    public void removeBook(String id) {
        // Stream: tim book theo id, xoa neu co
        Optional<Book> book = findBookById(id);
        if (book.isEmpty()) {
            System.out.println("[!] Khong tim thay sach: " + id);
            return;
        }
        if (!book.get().isAvailable()) {
            System.out.println("[!] Sach dang duoc muon, khong the xoa!");
            return;
        }
        books.remove(book.get());
        repo.saveBooks(books);
        System.out.println("[OK] Da xoa sach: " + book.get().getTitle());
    }

    // -------------------------------------------------------
    // SACH — TIM KIEM & LOC voi Stream API
    // -------------------------------------------------------

    // Optional<T>: tra ve Optional.empty() neu khong co, tranh NullPointerException
    public Optional<Book> findBookById(String id) {
        return books.stream()
                .filter(b -> b.getId().equalsIgnoreCase(id))
                .findFirst(); // tra ve Optional<Book>
    }

    // Tim theo tu khoa trong tieu de hoac tac gia
    public List<Book> searchBooks(String keyword) {
        String kw = keyword.toLowerCase();
        return books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(kw)
                        || b.getAuthor().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    // Loc theo the loai
    public List<Book> filterByGenre(String genre) {
        return books.stream()
                .filter(b -> b.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    // Chi lay sach dang co san
    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(Book::isAvailable)        // method reference
                .sorted(Comparator.comparing(Book::getTitle)) // sap xep A-Z
                .collect(Collectors.toList());
    }

    // Sap xep theo nam xuat ban
    public List<Book> getSortedByYear(boolean descending) {
        Comparator<Book> comp = Comparator.comparingInt(Book::getPublishYear);
        if (descending) comp = comp.reversed();
        return books.stream().sorted(comp).collect(Collectors.toList());
    }

    // -------------------------------------------------------
    // THANH VIEN — CRUD
    // -------------------------------------------------------
    public void addMember(Member member) {
        if (findMemberById(member.getId()).isPresent()) {
            System.out.println("[!] Ma thanh vien da ton tai: " + member.getId());
            return;
        }
        members.add(member);
        repo.saveMembers(members);
        System.out.println("[OK] Da them thanh vien: " + member.getName());
    }

    public Optional<Member> findMemberById(String id) {
        return members.stream()
                .filter(m -> m.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<Member> searchMembers(String keyword) {
        String kw = keyword.toLowerCase();
        return members.stream()
                .filter(m -> m.getName().toLowerCase().contains(kw)
                        || m.getEmail().toLowerCase().contains(kw))
                .collect(Collectors.toList());
    }

    // -------------------------------------------------------
    // MUON SACH
    // -------------------------------------------------------
    public boolean borrowBook(String memberId, String bookId) {
        Optional<Member> member = findMemberById(memberId);
        Optional<Book>   book   = findBookById(bookId);

        if (member.isEmpty()) { System.out.println("[!] Khong tim thay thanh vien: " + memberId); return false; }
        if (book.isEmpty())   { System.out.println("[!] Khong tim thay sach: " + bookId);         return false; }

        Book   b = book.get();
        Member m = member.get();

        if (!b.isAvailable()) {
            System.out.println("[!] Sach '" + b.getTitle() + "' dang duoc muon!");
            return false;
        }

        if (!m.borrowBook(bookId)) return false; // Member tu kiem tra gioi han

        b.setAvailable(false);
        records.add(new BorrowRecord(memberId, bookId));

        // Luu ca 3 file vi du lieu thay doi
        repo.saveBooks(books);
        repo.saveMembers(members);
        repo.saveRecords(records);

        System.out.println("[OK] " + m.getName() + " da muon: " + b.getTitle());
        return true;
    }

    // -------------------------------------------------------
    // TRA SACH
    // -------------------------------------------------------
    public boolean returnBook(String memberId, String bookId) {
        Optional<Member> member = findMemberById(memberId);
        Optional<Book>   book   = findBookById(bookId);

        if (member.isEmpty()) { System.out.println("[!] Khong tim thay thanh vien: " + memberId); return false; }
        if (book.isEmpty())   { System.out.println("[!] Khong tim thay sach: " + bookId);         return false; }

        Member m = member.get();
        Book   b = book.get();

        if (!m.hasBorrowed(bookId)) {
            System.out.println("[!] Thanh vien nay khong muon quyen sach nay!");
            return false;
        }

        m.returnBook(bookId);
        b.setAvailable(true);

        // Cap nhat record: tim record chua tra, danh dau da tra
        records.stream()
                .filter(r -> r.getMemberId().equals(memberId)
                        && r.getBookId().equals(bookId)
                        && !r.isReturned())
                .findFirst()
                .ifPresent(BorrowRecord::markReturned); // method reference

        repo.saveBooks(books);
        repo.saveMembers(members);
        repo.saveRecords(records);

        System.out.println("[OK] " + m.getName() + " da tra: " + b.getTitle());
        return true;
    }

    // -------------------------------------------------------
    // THONG KE voi Stream
    // -------------------------------------------------------
    public void printStats() {
        System.out.println("\n--- THONG KE THU VIEN ---");
        System.out.println("Tong sach        : " + books.size());

        // Stream count()
        long available = books.stream().filter(Book::isAvailable).count();
        long borrowed  = books.size() - available;
        System.out.println("Sach co san      : " + available);
        System.out.println("Sach dang muon   : " + borrowed);
        System.out.println("Tong thanh vien  : " + members.size());
        System.out.println("Tong luot muon   : " + records.size());

        // Sach duoc muon nhieu nhat
        records.stream()
                .collect(Collectors.groupingBy(BorrowRecord::getBookId, Collectors.counting()))
                .entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .ifPresent(e -> {
                    findBookById(e.getKey()).ifPresent(b ->
                            System.out.println("Muon nhieu nhat  : " + b.getTitle()
                                    + " (" + e.getValue() + " luot)"));
                });

        // Thanh vien muon nhieu nhat
        members.stream()
                .max(Comparator.comparingInt(Member::getBorrowCount))
                .ifPresent(m -> System.out.println("TV tich cuc nhat : "
                        + m.getName() + " (" + m.getBorrowCount() + " sach)"));

        System.out.println();
    }

    // -------------------------------------------------------
    // PRINT HELPERS
    // -------------------------------------------------------
    public void printBooks(List<Book> list) {
        if (list.isEmpty()) { System.out.println("  (Khong co sach nao)"); return; }
        System.out.println("+--------+--------------------------------+----------------------+--------------+------+------------+");
        System.out.println("| Ma     | Tieu de                        | Tac gia              | The loai     | Nam  | Trang thai |");
        System.out.println("+--------+--------------------------------+----------------------+--------------+------+------------+");
        for (Book b : list) System.out.println("| " + b + " |");
        System.out.println("+--------+--------------------------------+----------------------+--------------+------+------------+");
    }

    public void printAllBooks()    { printBooks(books); }

    public void printMembers(List<Member> list) {
        if (list.isEmpty()) { System.out.println("  (Khong co thanh vien nao)"); return; }
        System.out.println("+----------+----------------------+---------------------------+-----------+");
        System.out.println("| Ma TV    | Ten                  | Email                     | Dang muon |");
        System.out.println("+----------+----------------------+---------------------------+-----------+");
        for (Member m : list) System.out.println("| " + m + " |");
        System.out.println("+----------+----------------------+---------------------------+-----------+");
    }

    public void printAllMembers()  { printMembers(members); }

    public void printBorrowHistory(String memberId) {
        System.out.println("\n--- LICH SU MUON: " + memberId + " ---");
        List<BorrowRecord> history = records.stream()
                .filter(r -> r.getMemberId().equalsIgnoreCase(memberId))
                .sorted(Comparator.comparing(BorrowRecord::getBorrowDate).reversed())
                .collect(Collectors.toList());

        if (history.isEmpty()) { System.out.println("  (Chua co lich su)"); }
        else history.forEach(r -> System.out.println("  " + r));
        System.out.println();
    }

    public void printAllRecords() {
        System.out.println("\n--- LICH SU MUON SACH ---");
        if (records.isEmpty()) { System.out.println("  (Chua co lich su)"); }
        else records.forEach(r -> System.out.println("  " + r));
        System.out.println();
    }
}