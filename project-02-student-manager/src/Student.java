/**
 * CLASS: Student
 * ==============
 * Dai dien cho 1 sinh vien trong he thong.
 *
 * Kien thuc:
 * - Class & Object
 * - Constructor (co tham so)
 * - Encapsulation: private field + public getter/setter
 * - @Override toString() de in dep
 */
public class Student {

    // -------------------------------------------------------
    // FIELDS — private: ben ngoai khong truy cap truc tiep
    // -------------------------------------------------------
    private String id;       // Ma so sinh vien
    private String name;     // Ho ten
    private double gpa;      // Diem trung binh (0.0 - 10.0)
    private String major;    // Chuyen nganh

    // -------------------------------------------------------
    // CONSTRUCTOR — khoi tao object voi day du thong tin
    // -------------------------------------------------------
    public Student(String id, String name, double gpa, String major) {
        this.id    = id;
        this.name  = name;
        this.gpa   = gpa;
        this.major = major;
    }

    // -------------------------------------------------------
    // GETTERS — doc gia tri field tu ben ngoai
    // -------------------------------------------------------
    public String getId()     { return id; }
    public String getName()   { return name; }
    public double getGpa()    { return gpa; }
    public String getMajor()  { return major; }

    // -------------------------------------------------------
    // SETTERS — chi set nhung gi co the thay doi
    // id khong co setter vi MSSV khong doi sau khi tao
    // -------------------------------------------------------
    public void setName(String name) {
        if (name == null || name.isBlank()) {
            System.out.println("[!] Ten khong duoc de trong!");
            return;
        }
        this.name = name;
    }

    public void setGpa(double gpa) {
        if (gpa < 0 || gpa > 10) {
            System.out.println("[!] GPA phai trong khoang 0.0 - 10.0!");
            return;
        }
        this.gpa = gpa;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    // -------------------------------------------------------
    // HELPER: xep loai hoc luc dua tren GPA
    // -------------------------------------------------------
    public String getRank() {
        if (gpa >= 9.0) return "Xuat sac";
        if (gpa >= 8.0) return "Gioi";
        if (gpa >= 6.5) return "Kha";
        if (gpa >= 5.0) return "Trung binh";
        return "Yeu";
    }

    // -------------------------------------------------------
    // toString — tu dong goi khi print(student)
    // @Override = ghi de phuong thuc cua class cha (Object)
    // -------------------------------------------------------
    @Override
    public String toString() {
        return String.format("%-10s | %-20s | GPA: %4.1f | %-10s | %s",
                id, name, gpa, getRank(), major);
    }
}