package model;

/**
 * CLASS: Book
 * ============
 * Dai dien cho 1 quyen sach trong thu vien.
 *
 * Kien thuc:
 * - Builder Pattern: tao object phuc tap ma khong can nhieu constructor
 * - Thay vi: new Book(id, title, author, genre, year, available)
 *   Dung : Book.builder().id("B001").title("...").build()
 */
public class Book {

    private String id;
    private String title;
    private String author;
    private String genre;
    private int    publishYear;
    private boolean available; // true = co the muon, false = dang duoc muon

    // Constructor private — chi tao qua Builder
    private Book() {}

    // -------------------------------------------------------
    // GETTERS
    // -------------------------------------------------------
    public String  getId()          { return id; }
    public String  getTitle()       { return title; }
    public String  getAuthor()      { return author; }
    public String  getGenre()       { return genre; }
    public int     getPublishYear() { return publishYear; }
    public boolean isAvailable()    { return available; }

    public void setAvailable(boolean available) { this.available = available; }

    // -------------------------------------------------------
    // BUILDER PATTERN
    // -------------------------------------------------------
    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private Book book = new Book();

        public Builder id(String id)                { book.id = id;                   return this; }
        public Builder title(String title)          { book.title = title;             return this; }
        public Builder author(String author)        { book.author = author;           return this; }
        public Builder genre(String genre)          { book.genre = genre;             return this; }
        public Builder publishYear(int year)        { book.publishYear = year;        return this; }
        public Builder available(boolean available) { book.available = available;     return this; }

        public Book build() {
            if (book.id == null || book.title == null || book.author == null) {
                throw new IllegalStateException("Book phai co id, title, author!");
            }
            return book;
        }
    }

    // -------------------------------------------------------
    // Chuyen thanh CSV de luu file: id,title,author,genre,year,available
    // -------------------------------------------------------
    public String toCsv() {
        return String.join(",", id, title, author, genre,
                String.valueOf(publishYear), String.valueOf(available));
    }

    // Doc tu dong CSV
    public static Book fromCsv(String line) {
        String[] parts = line.split(",", 6);
        return Book.builder()
                .id(parts[0].trim())
                .title(parts[1].trim())
                .author(parts[2].trim())
                .genre(parts[3].trim())
                .publishYear(Integer.parseInt(parts[4].trim()))
                .available(Boolean.parseBoolean(parts[5].trim()))
                .build();
    }

    @Override
    public String toString() {
        return String.format("%-6s | %-30s | %-20s | %-12s | %d | %s",
                id, title, author, genre, publishYear,
                available ? "Co san" : "Dang muon");
    }
}