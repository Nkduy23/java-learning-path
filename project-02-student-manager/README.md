# Project 02 - Student Manager

> Project thu 2 trong lo trinh hoc Java — buoc dau tien vao OOP.
> Thay vi nhoi het vao 1 file nhu P01, gio tach thanh 3 class co trach nhiem rieng biet.

---

## Tinh nang

| # | Tinh nang | Mo ta |
|---|-----------|-------|
| 1 | Them sinh vien | Nhap MSSV, ho ten, GPA, chuyen nganh — co kiem tra trung MSSV |
| 2 | Xem danh sach | Hien thi toan bo sinh vien dang quan ly |
| 3 | Tim theo MSSV | Tim chinh xac 1 sinh vien bang ma so |
| 4 | Tim theo ten | Tim kiem mo (co the nhap 1 phan ten) |
| 5 | Loc theo nganh | Hien thi sinh vien cung chuyen nganh |
| 6 | Sap xep GPA | Cao -> thap hoac thap -> cao |
| 7 | Sap xep ten | A -> Z |
| 8 | Xoa sinh vien | Co buoc xac nhan truoc khi xoa |
| 9 | Thong ke | Tong so, GPA trung binh, sinh vien xuat sac nhat |

---

## Cau truc file

```
project-02-student-manager/
├── src/
│   ├── Student.java         <- Model: bieu dien 1 sinh vien
│   ├── StudentManager.java  <- Service: quan ly danh sach
│   └── Main.java            <- Entry point: menu + xu ly input
└── README.md
```

### Student.java — Model
Dai dien cho 1 sinh vien. Chua du lieu va logic lien quan den sinh vien do.
- `private` field: `id`, `name`, `gpa`, `major`
- Constructor nhan day du 4 tham so
- Getter cho tat ca field; setter chi cho nhung field co the thay doi (khong co `setId`)
- Setter co validation: GPA phai 0–10, ten khong duoc trong
- `getRank()`: tra ve xep loai hoc luc dua tren GPA
- `toString()`: dinh dang output dep khi in ra terminal

### StudentManager.java — Service
Quan ly toan bo danh sach. `Main.java` chi goi cac method o day, khong tu xu ly logic.
- `addStudent()`: kiem tra trung MSSV truoc khi them
- `removeStudent()`: xoa theo MSSV
- `findById()`: tim chinh xac, tra ve `null` neu khong co
- `findByName()`: tim mo bang keyword, tra ve `ArrayList`
- `filterByMajor()`: loc theo chuyen nganh
- `getSortedByGpa()`: tra ve ban sao da sap xep (khong doi list goc)
- `getSortedByName()`: sap xep A-Z
- `getAverageGpa()`, `getTopStudent()`, `getTotalStudents()`: thong ke

### Main.java — Entry Point
Xu ly tuong tac voi nguoi dung: in menu, doc input, goi StudentManager.
- Chua du lieu mau 7 sinh vien (`seedData()`) de test ngay khi chay
- `readDouble()`: doc so thuc co retry neu nhap sai

---

## Kien thuc hoc duoc

### OOP — lan dau tien viet Class thuc su
```java
// Tao object Student
Student s = new Student("SV001", "Nguyen Van An", 8.5, "CNTT");

// Truy cap qua getter (khong truy cap field truc tiep)
s.getName();   // "Nguyen Van An"
s.getGpa();    // 8.5
s.getRank();   // "Gioi"
```

### Encapsulation — bao dong du lieu
```java
// field la private — khong the truy cap tu ben ngoai
s.gpa = 15;         // LOI: compile error

// Phai di qua setter — co validation ben trong
s.setGpa(15);       // In canh bao: GPA phai trong khoang 0.0 - 10.0
s.setGpa(9.0);      // OK
```

### ArrayList — danh sach dong
```java
ArrayList<Student> students = new ArrayList<>();
students.add(s);           // them
students.remove(s);        // xoa
students.size();           // so luong
students.isEmpty();        // kiem tra trong
for (Student st : students) { ... }  // duyet
```

### Comparator — sap xep linh hoat
```java
// Sap xep theo GPA tang dan
sorted.sort(Comparator.comparingDouble(Student::getGpa));

// Giam dan
sorted.sort(Comparator.comparingDouble(Student::getGpa).reversed());

// Sap xep theo ten A-Z
sorted.sort(Comparator.comparing(Student::getName));
```

### Phan chia trach nhiem (tach layer)
```
Nguoi dung  <-->  Main.java  <-->  StudentManager.java  <-->  Student.java
               (menu/input)      (logic nghiep vu)           (du lieu)
```
Day la pattern Model - Service - Controller — nen tang cua moi project Java thuc te.

---

## Cach chay

```bash
# Compile tat ca file cung luc (vi cac class phu thuoc nhau)
cd project-02-student-manager/src
javac *.java

# Chay — class co main() la Main
java Main
```

---

## Demo nhanh

```
==============================
   Student Manager v1.0
   Project 02 / 05
==============================

+------------------------------+
|  1. Them sinh vien           |
|  2. Xem danh sach            |
|  3. Tim kiem / Loc           |
|  4. Sap xep                  |
|  5. Xoa sinh vien            |
|  6. Thong ke                 |
|  0. Thoat                    |
+------------------------------+
>> Chon: 6

--- THONG KE ---
Tong so sinh vien : 7
GPA trung binh    : 7.34
Sinh vien xuat sac: Vo Thanh Tung (GPA: 9.8)
```

---

## Push GitHub

```bash
git add .
git commit -m "feat: complete project 02 student manager"
git push origin main
```

---

## So sanh voi Project 01

| | Project 01 | Project 02 |
|--|-----------|-----------|
| So file | 1 | 3 |
| To chuc code | Tat ca trong 1 class | Tach Model / Service / Main |
| Du lieu | String don gian | Object (Student) |
| Luu tru | ArrayList<String> | ArrayList<Student> |
| Tim kiem | Khong co | Co (theo MSSV, ten, nganh) |
| Sap xep | Khong co | Co (GPA, ten) |