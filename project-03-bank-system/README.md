# Project 03 - Bank System

> Project thu 3 — buoc nhay lon vao OOP nang cao: Inheritance, abstract class, Exception Handling.
> Lan dau tien dung **package** de tach code theo chuc nang nhu du an thuc te.

---

## Tinh nang

| # | Tinh nang | Mo ta |
|---|-----------|-------|
| 1 | Tao tai khoan | 3 loai: Thanh toan / Tiet kiem / VIP |
| 2 | Nap tien | Kiem tra so tien > 0 |
| 3 | Rut tien | Moi loai TK co rule rieng (han muc, so du toi thieu) |
| 4 | Chuyen khoan | Rut tu A, nap vao B — rollback neu that bai |
| 5 | Tinh lai suat | Chi cho tai khoan Tiet kiem |
| 6 | Xem lich su | Toan bo giao dich co timestamp |
| 7 | Xem chi tiet | Hien thi thong tin rieng tung loai TK |

---

## Cau truc file

```
project-03-bank-system/
├── src/
│   ├── Main.java                      <- Entry point: menu + input
│   ├── model/
│   │   ├── Account.java               <- Abstract class: lop co so
│   │   ├── CheckingAccount.java       <- Tai khoan thanh toan
│   │   ├── SavingsAccount.java        <- Tai khoan tiet kiem (co lai suat)
│   │   └── PremiumAccount.java        <- Tai khoan VIP (ho tro overdraft)
│   ├── service/
│   │   └── BankService.java           <- Xu ly toan bo nghiep vu ngan hang
│   └── exception/
│       └── BankExceptions.java        <- Custom exception classes
└── README.md
```

---

## Kien thuc hoc duoc

### 1. Abstract Class & Inheritance
```java
// Account la abstract — khong the tao truc tiep
Account acc = new Account(...);  // LOI: compile error

// Phai tao qua lop con cu the
Account acc = new CheckingAccount("TT001", "Nguyen Van", 5_000_000);
Account sav = new SavingsAccount("TK001", "Tran Thi",   10_000_000, 0.7);
Account vip = new PremiumAccount("VP001", "Le Hoang",   200_000_000);

// Polymorphism: goi cung 1 method, moi loai xu ly khac nhau
acc.withdraw(1_000_000); // CheckingAccount.validateWithdraw()
sav.withdraw(1_000_000); // SavingsAccount.validateWithdraw() — check so du toi thieu
vip.withdraw(1_000_000); // PremiumAccount.validateWithdraw() — cho phep overdraft
```

### 2. Abstract Method vs Override
```java
// Abstract method: lop con BUOC PHAI implement
public abstract String getAccountType();
public abstract double getWithdrawLimit();

// Lop con override
@Override
public String getAccountType() { return "Tiet kiem"; }

@Override
public double getWithdrawLimit() { return 20_000_000; }
```

### 3. super() — goi constructor / method lop cha
```java
public SavingsAccount(String number, String owner, double balance, double rate) {
    super(number, owner, balance); // goi Account constructor
    this.interestRate = rate;      // them field rieng
}

@Override
protected void validateWithdraw(double amount) throws Exception {
    super.validateWithdraw(amount); // tan dung logic chung cua Account
    // Sau do them rule rieng cua SavingsAccount
    if (balance - amount < MIN_BALANCE) throw new Exception("...");
}
```

### 4. instanceof & Pattern Matching (Java 16+)
```java
// Kiem tra kieu va ep kieu cung 1 buoc
if (acc instanceof SavingsAccount savings) {
    savings.applyInterest(); // dung truc tiep, khong can cast
}

if (acc instanceof PremiumAccount p) {
    System.out.println("Overdraft: " + p.getOverdraftLimit());
}
```

### 5. Exception Handling
```java
// try/catch bat dung loai loi
try {
    account.withdraw(amount);
} catch (IllegalArgumentException e) {
    // So tien <= 0
    System.out.println("So tien khong hop le: " + e.getMessage());
} catch (Exception e) {
    // So du khong du, vuot han muc...
    System.out.println("Giao dich that bai: " + e.getMessage());
}

// Custom exception ro rang hon Exception chung
throw new RuntimeException("Khong tim thay tai khoan: " + number);
```

### 6. HashMap<String, Account>
```java
HashMap<String, Account> accounts = new HashMap<>();

accounts.put("TT001", new CheckingAccount(...));  // them
accounts.get("TT001");                             // lay O(1)
accounts.containsKey("TT001");                     // kiem tra ton tai
accounts.values();                                 // lay toan bo Account
```

---

## Quy tac tung loai tai khoan

| Loai | Han muc rut/lan | So du toi thieu | Dac biet |
|------|----------------|-----------------|---------|
| Thanh toan | 50 trieu | 0 | Co ban |
| Tiet kiem | 20 trieu | 1 trieu | Co lai suat |
| VIP | 200 trieu | -10 trieu | Ho tro overdraft 10tr |

---

## Cach chay

```bash
cd project-03-bank-system/src
javac -d . model/*.java exception/*.java service/*.java Main.java
java Main
```

> `-d .` : compile ra file .class theo dung cau truc package

---

## Push GitHub

```bash
git add .
git commit -m "feat: complete project 03 bank system"
git push origin main
```

---

## So sanh voi Project 02

| | Project 02 | Project 03 |
|--|-----------|-----------|
| OOP | Class don gian | Abstract class + Inheritance |
| Lop con | Khong co | 3 loai Account ke thua |
| Xu ly loi | In thong bao | try/catch + custom Exception |
| Luu tru | ArrayList | HashMap (tim kiem O(1)) |
| Package | Khong co | model / service / exception |
| Timestamp | Khong co | LocalDateTime ghi moi giao dich |