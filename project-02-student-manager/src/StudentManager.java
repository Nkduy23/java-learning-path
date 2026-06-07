import java.util.ArrayList;
import java.util.Comparator;

/**
 * CLASS: StudentManager
 * =====================
 * Quan ly danh sach sinh vien — xu ly toan bo logic nghiep vu.
 * Main.java chi goi cac method cua class nay, khong tu xu ly.
 *
 * Kien thuc:
 * - ArrayList<Student>: them, xoa, duyet
 * - for-each loop
 * - Comparator: sap xep theo nhieu tieu chi
 * - Stream (don gian): loc danh sach
 * - static method vs instance method
 */
public class StudentManager {

    // -------------------------------------------------------
    // FIELD: danh sach sinh vien (song suot vong doi chuong trinh)
    // -------------------------------------------------------
    private ArrayList<Student> students = new ArrayList<>();

    // -------------------------------------------------------
    // THEM sinh vien
    // -------------------------------------------------------
    public boolean addStudent(Student s) {
        // Kiem tra trung MSSV truoc khi them
        if (findById(s.getId()) != null) {
            System.out.println("[!] MSSV " + s.getId() + " da ton tai!");
            return false;
        }
        students.add(s);
        System.out.println("[OK] Da them: " + s.getName());
        return true;
    }

    // -------------------------------------------------------
    // XOA sinh vien theo MSSV
    // -------------------------------------------------------
    public boolean removeStudent(String id) {
        Student target = findById(id);
        if (target == null) {
            System.out.println("[!] Khong tim thay MSSV: " + id);
            return false;
        }
        students.remove(target);
        System.out.println("[OK] Da xoa: " + target.getName());
        return true;
    }

    // -------------------------------------------------------
    // TIM KIEM theo MSSV — tra ve null neu khong co
    // -------------------------------------------------------
    public Student findById(String id) {
        for (Student s : students) {          // for-each loop
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // -------------------------------------------------------
    // TIM KIEM theo ten — tra ve danh sach (co the nhieu nguoi trung ten)
    // -------------------------------------------------------
    public ArrayList<Student> findByName(String keyword) {
        ArrayList<Student> result = new ArrayList<>();
        String kw = keyword.toLowerCase();

        for (Student s : students) {
            if (s.getName().toLowerCase().contains(kw)) {
                result.add(s);
            }
        }
        return result;
    }

    // -------------------------------------------------------
    // LOC theo chuyen nganh
    // -------------------------------------------------------
    public ArrayList<Student> filterByMajor(String major) {
        ArrayList<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getMajor().equalsIgnoreCase(major)) {
                result.add(s);
            }
        }
        return result;
    }

    // -------------------------------------------------------
    // SAP XEP — tra ve ban sao da sap xep, khong doi list goc
    // Comparator.comparingDouble: so sanh theo GPA
    // .reversed(): dao nguoc (cao -> thap)
    // -------------------------------------------------------
    public ArrayList<Student> getSortedByGpa(boolean descending) {
        ArrayList<Student> sorted = new ArrayList<>(students); // copy
        Comparator<Student> comp = Comparator.comparingDouble(Student::getGpa);
        if (descending) comp = comp.reversed();
        sorted.sort(comp);
        return sorted;
    }

    public ArrayList<Student> getSortedByName() {
        ArrayList<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparing(Student::getName));
        return sorted;
    }

    // -------------------------------------------------------
    // THONG KE
    // -------------------------------------------------------
    public double getAverageGpa() {
        if (students.isEmpty()) return 0;
        double total = 0;
        for (Student s : students) total += s.getGpa();
        return total / students.size();
    }

    public Student getTopStudent() {
        if (students.isEmpty()) return null;
        Student top = students.get(0);
        for (Student s : students) {
            if (s.getGpa() > top.getGpa()) top = s;
        }
        return top;
    }

    public int getTotalStudents() {
        return students.size();
    }

    // -------------------------------------------------------
    // IN DANH SACH
    // -------------------------------------------------------
    public void printList(ArrayList<Student> list) {
        if (list.isEmpty()) {
            System.out.println("  (Danh sach trong)");
            return;
        }
        System.out.println("+----------+----------------------+----------+------------+------------------+");
        System.out.println("| MSSV     | Ho Ten               | GPA      | Xep loai   | Chuyen nganh     |");
        System.out.println("+----------+----------------------+----------+------------+------------------+");
        for (Student s : list) {
            System.out.println("| " + s + " |");
        }
        System.out.println("+----------+----------------------+----------+------------+------------------+");
    }

    public void printAll() {
        printList(students);
    }
}