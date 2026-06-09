package com.ai360.ecommerce.config;

import com.ai360.ecommerce.entity.*;
import com.ai360.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * DATA SEEDER
 * ============
 * CommandLineRunner: chay tu dong sau khi Spring Boot khoi dong xong.
 * Nap du lieu mau vao H2 database de test ngay.
 *
 * @Component: Spring tu dong phat hien va chay khi startup.
 */
@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Chi seed neu chua co du lieu
        if (userRepository.count() > 0) return;

        // --- USERS ---
        User admin = new User();
        admin.setFullName("Admin");
        admin.setEmail("admin@ai360.asia");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(User.Role.ADMIN);
        userRepository.save(admin);

        User user = new User();
        user.setFullName("Nguyen Van An");
        user.setEmail("an@email.com");
        user.setPassword(passwordEncoder.encode("user123"));
        user.setRole(User.Role.USER);
        userRepository.save(user);

        // --- CATEGORIES ---
        Category electronics = new Category();
        electronics.setName("Dien tu");
        electronics.setDescription("Thiet bi dien tu, smartphone, laptop");
        categoryRepository.save(electronics);

        Category clothing = new Category();
        clothing.setName("Thoi trang");
        clothing.setDescription("Quan ao, giay dep, phu kien");
        categoryRepository.save(clothing);

        Category books = new Category();
        books.setName("Sach");
        books.setDescription("Sach giao khoa, truyen, ky nang");
        categoryRepository.save(books);

        // --- PRODUCTS ---
        Product p1 = new Product();
        p1.setName("iPhone 15 Pro");
        p1.setDescription("Smartphone cao cap cua Apple, chip A17 Pro");
        p1.setPrice(new BigDecimal("29990000"));
        p1.setStock(50);
        p1.setCategory(electronics);
        productRepository.save(p1);

        Product p2 = new Product();
        p2.setName("Samsung Galaxy S24");
        p2.setDescription("Flagship Android, man hinh AMOLED 120Hz");
        p2.setPrice(new BigDecimal("22990000"));
        p2.setStock(30);
        p2.setCategory(electronics);
        productRepository.save(p2);

        Product p3 = new Product();
        p3.setName("MacBook Air M3");
        p3.setDescription("Laptop sieu mong nhe, chip M3 manh me");
        p3.setPrice(new BigDecimal("32990000"));
        p3.setStock(20);
        p3.setCategory(electronics);
        productRepository.save(p3);

        Product p4 = new Product();
        p4.setName("Ao thun basic trang");
        p4.setDescription("Chat lieu cotton 100%, thoai mai");
        p4.setPrice(new BigDecimal("199000"));
        p4.setStock(200);
        p4.setCategory(clothing);
        productRepository.save(p4);

        Product p5 = new Product();
        p5.setName("Clean Code");
        p5.setDescription("Robert C. Martin - Viet code sach dep");
        p5.setPrice(new BigDecimal("320000"));
        p5.setStock(100);
        p5.setCategory(books);
        productRepository.save(p5);

        System.out.println("\n[SEED] Da tao: 2 users, 3 categories, 5 products");
        System.out.println("[SEED] Admin: admin@ai360.asia / admin123");
        System.out.println("[SEED] User : an@email.com / user123\n");
    }
}
