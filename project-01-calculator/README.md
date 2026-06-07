# Project 01 — Calculator CLI ☕

Máy tính dòng lệnh đơn giản — project đầu tiên trong lộ trình học Java.

## Tính năng

- ✅ Tính toán với 5 phép: `+` `-` `*` `/` `%`
- ✅ Xử lý lỗi: chia cho 0, nhập sai kiểu số
- ✅ Lịch sử phép tính trong session
- ✅ Xóa lịch sử

## Kiến thức áp dụng

| Kiến thức | Dùng ở đâu |
|-----------|-----------|
| `double`, `String`, `boolean` | Kiểu dữ liệu biến |
| `Scanner` | Đọc input từ bàn phím |
| `if / switch` | Rẽ nhánh xử lý |
| `while (true)` | Vòng lặp retry khi nhập sai |
| `method` | Tách logic thành hàm riêng |
| `ArrayList<String>` | Lưu lịch sử |
| `try / catch` | Xử lý lỗi `NumberFormatException` |
| `Double.NaN` | Đánh dấu kết quả không hợp lệ |

## Cách chạy

```bash
# Compile
cd project-01-calculator/src
javac Calculator.java

# Chạy
java Calculator
```

## Demo

```
╔══════════════════════════════╗
║   ☕ Java Calculator CLI     ║
║      Project 01 / 05         ║
╚══════════════════════════════╝

┌──────────────────────────────┐
│  1. Tính toán                │
│  2. Xem lịch sử              │
│  3. Xóa lịch sử              │
│  0. Thoát                    │
└──────────────────────────────┘
👉 Chọn: 1

─────────────────────────────
Nhập số thứ nhất: 10
Chọn phép tính: + | - | * | / | %
Toán tử: /
Nhập số thứ hai : 3
✅ Kết quả: 10 / 3 = 3.333333
─────────────────────────────
```

## Push lên GitHub

```bash
git add .
git commit -m "feat: complete project 01 calculator CLI"
git push origin main
```