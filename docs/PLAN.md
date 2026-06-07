# 📋 PLAN.md — Kế hoạch học Java qua 5 Project

---

## 🎯 Mục tiêu tổng thể

Sau khi hoàn thành 5 project này, bạn sẽ:
- Nắm chắc Java core (syntax, OOP, Collections, I/O)
- Hiểu cách tổ chức code theo cấu trúc thực tế
- Biết dùng Spring Boot để build REST API
- Có 5 project thực tế trên GitHub để portfolio

---

## 📦 PROJECT 01 — Calculator CLI

**Thư mục:** `project-01-calculator/`
**Thời gian dự kiến:** 1–2 ngày

### Mô tả
Máy tính dòng lệnh (command-line). Người dùng nhập phép tính, chương trình trả kết quả. Hỗ trợ các phép tính cơ bản và lịch sử tính toán.

### Tính năng
- [ ] Nhập 2 số và chọn phép tính (+, -, *, /, %)
- [ ] Xử lý chia cho 0 (in thông báo lỗi, không crash)
- [ ] Vòng lặp: tính tiếp hoặc thoát
- [ ] Hiển thị lịch sử các phép tính trong session

### Kiến thức học được
- `int`, `double`, `String`, `boolean` — kiểu dữ liệu cơ bản
- `Scanner` — đọc input từ bàn phím
- `if / else if / switch` — rẽ nhánh
- `while / for` — vòng lặp
- `method` — tách logic thành hàm
- `ArrayList<String>` — lưu lịch sử (giới thiệu sơ)

### Cấu trúc file
```
project-01-calculator/
├── src/
│   └── Calculator.java
└── README.md
```

---

## 📦 PROJECT 02 — Student Manager

**Thư mục:** `project-02-student-manager/`
**Thời gian dự kiến:** 2–3 ngày

### Mô tả
Hệ thống quản lý sinh viên chạy trên terminal. Thêm, xem, tìm kiếm, sắp xếp danh sách sinh viên theo điểm.

### Tính năng
- [ ] Thêm sinh viên (tên, MSSV, điểm)
- [ ] Xem danh sách toàn bộ
- [ ] Tìm kiếm theo tên hoặc MSSV
- [ ] Sắp xếp theo điểm (tăng/giảm)
- [ ] Xóa sinh viên
- [ ] Tính điểm trung bình lớp

### Kiến thức học được
- **Class & Object** — tạo class `Student`
- **Constructor** — khởi tạo đối tượng
- **Encapsulation** — `private` field + `getter/setter`
- **ArrayList<Student>** — quản lý danh sách đối tượng
- **Comparable / Comparator** — sắp xếp
- **for-each loop**
- **static method**

### Cấu trúc file
```
project-02-student-manager/
├── src/
│   ├── Student.java
│   ├── StudentManager.java
│   └── Main.java
└── README.md
```

---

## 📦 PROJECT 03 — Bank System

**Thư mục:** `project-03-bank-system/`
**Thời gian dự kiến:** 3–4 ngày

### Mô tả
Hệ thống ngân hàng đơn giản. Nhiều loại tài khoản (thường, tiết kiệm, VIP) với quy tắc khác nhau. Xử lý lỗi nghiêm túc khi giao dịch không hợp lệ.

### Tính năng
- [ ] Tạo tài khoản (Checking / Savings / Premium)
- [ ] Nạp tiền, rút tiền (kiểm tra số dư)
- [ ] Chuyển khoản giữa 2 tài khoản
- [ ] Xem lịch sử giao dịch
- [ ] Tính lãi suất cho tài khoản tiết kiệm
- [ ] Xử lý lỗi: số dư không đủ, tài khoản không tồn tại

### Kiến thức học được
- **Inheritance** — `Account` → `SavingsAccount`, `PremiumAccount`
- **Abstract class** — `Account` là abstract
- **Interface** — `Transferable`, `InterestBearing`
- **Exception Handling** — `try/catch/finally`, custom Exception
- **`throws` / `throw`**
- **`instanceof`**
- **HashMap<String, Account>** — lưu tài khoản theo số TK

### Cấu trúc file
```
project-03-bank-system/
├── src/
│   ├── model/
│   │   ├── Account.java          (abstract)
│   │   ├── CheckingAccount.java
│   │   ├── SavingsAccount.java
│   │   └── PremiumAccount.java
│   ├── exception/
│   │   ├── InsufficientFundsException.java
│   │   └── AccountNotFoundException.java
│   ├── service/
│   │   └── BankService.java
│   └── Main.java
└── README.md
```

---

## 📦 PROJECT 04 — Library Manager

**Thư mục:** `project-04-library-manager/`
**Thời gian dự kiến:** 4–5 ngày

### Mô tả
Hệ thống quản lý thư viện với dữ liệu được lưu ra file (không mất khi tắt chương trình). Áp dụng design pattern và Java hiện đại (Lambda, Stream).

### Tính năng
- [ ] Quản lý sách: thêm, xóa, tìm kiếm, lọc theo thể loại
- [ ] Quản lý thành viên
- [ ] Mượn / trả sách (kiểm tra còn sách không)
- [ ] Lưu dữ liệu vào file JSON/CSV, đọc lại khi khởi động
- [ ] Thống kê: sách mượn nhiều nhất, thành viên tích cực nhất
- [ ] Tìm kiếm nâng cao: lọc nhiều điều kiện cùng lúc

### Kiến thức học được
- **Collections nâng cao:** `HashMap`, `HashSet`, `LinkedList`, `TreeMap`
- **File I/O:** `FileReader`, `FileWriter`, `BufferedReader`
- **Serialization** (đọc/ghi object ra file)
- **Lambda Expression** — `(x) -> x.getTitle()`
- **Stream API** — `filter()`, `map()`, `sorted()`, `collect()`
- **Optional<T>**
- **Singleton Pattern** — `LibraryManager`
- **Builder Pattern** — tạo `Book` object

### Cấu trúc file
```
project-04-library-manager/
├── src/
│   ├── model/
│   │   ├── Book.java
│   │   ├── Member.java
│   │   └── BorrowRecord.java
│   ├── service/
│   │   ├── BookService.java
│   │   ├── MemberService.java
│   │   └── BorrowService.java
│   ├── repository/
│   │   └── FileRepository.java
│   ├── util/
│   │   └── DataLoader.java
│   └── Main.java
├── data/
│   ├── books.csv
│   └── members.csv
└── README.md
```

---

## 📦 PROJECT 05 — E-commerce REST API

**Thư mục:** `project-05-ecommerce-api/`
**Thời gian dự kiến:** 7–10 ngày

### Mô tả
REST API cho một hệ thống thương mại điện tử nhỏ. Build bằng Spring Boot, kết nối MySQL, có authentication JWT cơ bản. Đây là project gần giống môi trường công ty nhất.

### Tính năng
- [ ] Auth: đăng ký, đăng nhập (JWT token)
- [ ] CRUD sản phẩm (Product)
- [ ] CRUD danh mục (Category)
- [ ] Giỏ hàng (Cart): thêm/xóa sản phẩm
- [ ] Đặt hàng (Order)
- [ ] Phân trang và tìm kiếm sản phẩm
- [ ] Swagger UI để test API

### Kiến thức học được
- **Spring Boot** — auto-configuration, application context
- **Spring MVC** — `@RestController`, `@GetMapping`, `@PostMapping`
- **Spring Data JPA** — `@Entity`, `@Repository`, query methods
- **MySQL / H2** — kết nối database thật
- **DTO Pattern** — tách request/response khỏi entity
- **JWT Authentication** — `spring-security`
- **Validation** — `@Valid`, `@NotNull`, `@Size`
- **Exception Handler** — `@ControllerAdvice`
- **Swagger / OpenAPI** — tài liệu hóa API
- **Maven** — quản lý dependency

### Cấu trúc file
```
project-05-ecommerce-api/
├── src/main/java/com/ai360/ecommerce/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── entity/
│   ├── dto/
│   ├── security/
│   └── exception/
├── src/main/resources/
│   └── application.properties
├── pom.xml
└── README.md
```

---

## 📅 Timeline gợi ý

```
Tuần 1:  Project 01 + 02  (Java cơ bản + OOP)
Tuần 2:  Project 03       (OOP nâng cao + Exception)
Tuần 3:  Project 04       (Collections + I/O + Lambda)
Tuần 4-5: Project 05      (Spring Boot + REST API)
```

---

## ✅ Checklist hoàn thành mỗi project

Trước khi push GitHub, đảm bảo:
- [ ] Code chạy được, không có lỗi compile
- [ ] Có `README.md` giải thích project
- [ ] Code được comment ở những chỗ quan trọng
- [ ] Đặt tên biến/hàm/class rõ ràng, đúng convention Java
- [ ] Commit message rõ nghĩa (ví dụ: `feat: add student search by name`)

---

## 🔧 Công cụ cần cài

| Công cụ | Mục đích | Link |
|---------|----------|------|
| JDK 21 | Chạy Java | adoptium.net |
| IntelliJ IDEA Community | IDE miễn phí tốt nhất | jetbrains.com |
| Git + GitHub | Version control | github.com |
| Maven | Build tool (dùng từ P05) | mvnrepository.com |
| TablePlus hoặc DBeaver | Xem database (dùng từ P05) | |

---

*Cập nhật lần cuối: 2026-06-07*