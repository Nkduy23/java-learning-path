package util;

import model.Book;
import model.Member;
import service.LibraryService;

/**
 * UTILITY: DataSeeder
 * ====================
 * Nap du lieu mau vao he thong lan dau chay.
 * Tach ra khoi Main de giu Main gon gang.
 */
public class DataSeeder {

    public static void seed(LibraryService library) {
        // --- SACH ---
        library.addBook(Book.builder().id("B001").title("Lap trinh Java co ban").author("Nguyen Van A")
                .genre("Cong nghe").publishYear(2020).available(true).build());
        library.addBook(Book.builder().id("B002").title("Clean Code").author("Robert C. Martin")
                .genre("Cong nghe").publishYear(2008).available(true).build());
        library.addBook(Book.builder().id("B003").title("Dac Nhan Tam").author("Dale Carnegie")
                .genre("Ky nang").publishYear(1936).available(true).build());
        library.addBook(Book.builder().id("B004").title("Sapiens").author("Yuval Noah Harari")
                .genre("Lich su").publishYear(2011).available(true).build());
        library.addBook(Book.builder().id("B005").title("The Pragmatic Programmer").author("David Thomas")
                .genre("Cong nghe").publishYear(1999).available(true).build());
        library.addBook(Book.builder().id("B006").title("Nguoi Giau Nhat Babylon").author("George S. Clason")
                .genre("Tai chinh").publishYear(1926).available(true).build());
        library.addBook(Book.builder().id("B007").title("Design Patterns").author("Gang of Four")
                .genre("Cong nghe").publishYear(1994).available(true).build());

        // --- THANH VIEN ---
        library.addMember(new Member("M001", "Nguyen Van An",   "an@email.com"));
        library.addMember(new Member("M002", "Tran Thi Bich",   "bich@email.com"));
        library.addMember(new Member("M003", "Le Hoang Nam",    "nam@email.com"));

        System.out.println("[OK] Da nap du lieu mau!\n");
    }
}