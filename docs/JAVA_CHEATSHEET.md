# ☕ JAVA_CHEATSHEET.md — Syntax cần nhớ

---

## Kiểu dữ liệu cơ bản

```java
int age = 25;
double price = 9.99;
boolean isActive = true;
char grade = 'A';
String name = "Duy";
```

---

## Nhập input từ bàn phím

```java
import java.util.Scanner;
Scanner sc = new Scanner(System.in);

String name = sc.nextLine();
int num = sc.nextInt();
double price = sc.nextDouble();
```

---

## Điều kiện

```java
if (score >= 8) {
    System.out.println("Giỏi");
} else if (score >= 6.5) {
    System.out.println("Khá");
} else {
    System.out.println("Trung bình");
}

// Switch
switch (choice) {
    case 1 -> System.out.println("Chọn 1");
    case 2 -> System.out.println("Chọn 2");
    default -> System.out.println("Không hợp lệ");
}
```

---

## Vòng lặp

```java
// For
for (int i = 0; i < 10; i++) { }

// While
while (running) { }

// For-each
for (String item : list) { }
```

---

## Hàm (Method)

```java
public static int add(int a, int b) {
    return a + b;
}

// Gọi
int result = add(3, 5);
```

---

## Class & Object

```java
public class Student {
    private String name;
    private double gpa;

    public Student(String name, double gpa) {
        this.name = name;
        this.gpa = gpa;
    }

    public String getName() { return name; }
    public double getGpa() { return gpa; }

    @Override
    public String toString() {
        return name + " - GPA: " + gpa;
    }
}

// Tạo object
Student s = new Student("Duy", 3.8);
System.out.println(s.getName());
```

---

## ArrayList

```java
import java.util.ArrayList;
ArrayList<Student> students = new ArrayList<>();

students.add(new Student("Duy", 3.8));
students.size();
students.get(0);
students.remove(0);
students.contains(s);

for (Student st : students) {
    System.out.println(st);
}
```

---

## HashMap

```java
import java.util.HashMap;
HashMap<String, Integer> scores = new HashMap<>();

scores.put("Duy", 95);
scores.get("Duy");        // 95
scores.containsKey("Duy");
scores.remove("Duy");

for (String key : scores.keySet()) {
    System.out.println(key + ": " + scores.get(key));
}
```

---

## Exception Handling

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Lỗi: " + e.getMessage());
} finally {
    System.out.println("Luôn chạy");
}

// Custom exception
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
```

---

## Inheritance

```java
public abstract class Animal {
    protected String name;
    public abstract void speak();  // phải override
    public void breathe() { System.out.println("Breathing"); }
}

public class Dog extends Animal {
    @Override
    public void speak() { System.out.println("Woof!"); }
}
```

---

## Interface

```java
public interface Flyable {
    void fly();
    default void land() { System.out.println("Landing"); }
}

public class Bird implements Flyable {
    @Override
    public void fly() { System.out.println("Bird flying"); }
}
```

---

## Lambda & Stream (Java 8+)

```java
import java.util.stream.*;

List<Student> topStudents = students.stream()
    .filter(s -> s.getGpa() >= 3.5)
    .sorted((a, b) -> Double.compare(b.getGpa(), a.getGpa()))
    .collect(Collectors.toList());

// Map
List<String> names = students.stream()
    .map(Student::getName)
    .collect(Collectors.toList());

// Average
OptionalDouble avg = students.stream()
    .mapToDouble(Student::getGpa)
    .average();
```

---

## File I/O

```java
import java.io.*;

// Ghi file
try (FileWriter fw = new FileWriter("data.txt");
     BufferedWriter bw = new BufferedWriter(fw)) {
    bw.write("Hello World");
    bw.newLine();
}

// Đọc file
try (FileReader fr = new FileReader("data.txt");
     BufferedReader br = new BufferedReader(fr)) {
    String line;
    while ((line = br.readLine()) != null) {
        System.out.println(line);
    }
}
```