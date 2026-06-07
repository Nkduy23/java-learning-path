# 🐙 GIT_WORKFLOW.md — Hướng dẫn dùng Git & GitHub

---

## Lần đầu setup (chỉ làm 1 lần)

```bash
# 1. Cài Git: https://git-scm.com
# 2. Cấu hình tên và email
git config --global user.name "Tên của bạn"
git config --global user.email "email@example.com"

# 3. Tạo repo trên GitHub (nhấn "New repository")
#    Tên gợi ý: java-learning-path

# 4. Clone hoặc init local
git init
git remote add origin https://github.com/username/java-learning-path.git
```

---

## Workflow mỗi lần code xong

```bash
# Xem trạng thái file thay đổi
git status

# Stage tất cả file
git add .

# Hoặc stage file cụ thể
git add src/Calculator.java

# Commit với message rõ ràng
git commit -m "feat: add history feature to calculator"

# Push lên GitHub
git push origin main
```

---

## Convention cho commit message

```
feat:     Thêm tính năng mới
fix:      Sửa bug
refactor: Cải thiện code, không thêm tính năng
docs:     Cập nhật tài liệu
chore:    Cài thư viện, cấu hình
```

**Ví dụ thực tế:**
```
feat: add student search by name
fix: handle division by zero in calculator
refactor: extract calculation logic to separate method
docs: update README for project 02
```

---

## Branch cho từng project (tùy chọn)

```bash
# Tạo branch mới khi bắt đầu project mới
git checkout -b project-02-student-manager

# Sau khi done, merge vào main
git checkout main
git merge project-02-student-manager
git push origin main
```

---

## .gitignore (tạo file này ở root)

```gitignore
# IntelliJ
.idea/
*.iml
out/

# Maven
target/

# OS
.DS_Store
Thumbs.db

# Env
.env
```