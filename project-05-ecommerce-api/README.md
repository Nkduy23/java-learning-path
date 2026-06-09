# Project 05 - E-commerce REST API

> Project cuoi cung — xay dung REST API thuc te bang Spring Boot.
> Khac hoan toan 4 project truoc: khong con terminal menu, thay vao do la HTTP endpoints.

---

## Tinh nang

| Endpoint | Method | Mo ta | Auth |
|----------|--------|-------|------|
| `/api/auth/register` | POST | Dang ky tai khoan | Public |
| `/api/auth/login` | POST | Dang nhap, nhan JWT | Public |
| `/api/products` | GET | Danh sach san pham (phan trang) | Public |
| `/api/products/{id}` | GET | Chi tiet san pham | Public |
| `/api/products/search?keyword=` | GET | Tim kiem san pham | Public |
| `/api/products/category/{id}` | GET | Loc theo danh muc | Public |
| `/api/products` | POST | Tao san pham moi | **Token** |
| `/api/products/{id}` | PUT | Cap nhat san pham | **Token** |
| `/api/products/{id}` | DELETE | Xoa san pham | **Token** |
| `/api/categories` | GET/POST/PUT/DELETE | CRUD danh muc | GET public, rest **Token** |

---

## Cau truc file

```
project-05-ecommerce-api/
├── pom.xml                                    <- Maven: quan ly dependency
├── src/main/
│   ├── resources/
│   │   └── application.properties            <- Cau hinh DB, JWT, Swagger
│   └── java/com/ai360/ecommerce/
│       ├── EcommerceApplication.java          <- Entry point @SpringBootApplication
│       ├── entity/
│       │   ├── User.java                      <- Bang users
│       │   ├── Category.java                  <- Bang categories
│       │   └── Product.java                   <- Bang products
│       ├── dto/
│       │   └── Dto.java                       <- Request/Response objects
│       ├── repository/
│       │   └── Repositories.java              <- JpaRepository interfaces
│       ├── service/
│       │   └── Services.java                  <- Logic nghiep vu
│       ├── controller/
│       │   └── Controllers.java               <- REST endpoints
│       ├── security/
│       │   ├── JwtUtil.java                   <- Tao/xac thuc JWT
│       │   └── SecurityConfig.java            <- Spring Security + JWT filter
│       ├── exception/
│       │   └── GlobalExceptionHandler.java    <- Xu ly loi tap trung
│       └── config/
│           ├── DataSeeder.java                <- Nap du lieu mau luc startup
│           └── SwaggerConfig.java             <- Cau hinh Swagger UI
```

---

## Kien thuc hoc duoc

### 1. Spring Boot — Khoi dong he thong
```java
@SpringBootApplication  // = @Configuration + @EnableAutoConfiguration + @ComponentScan
public class EcommerceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args); // khoi dong embedded Tomcat
    }
}
```

### 2. Entity & JPA — Map voi database
```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")  // foreign key
    private Category category;

    @PrePersist  // tu dong chay truoc khi INSERT
    protected void onCreate() { createdAt = LocalDateTime.now(); }
}
```

### 3. Repository — Query database khong can viet SQL
```java
// Chi extends JpaRepository la co: save, findById, findAll, delete, count...
interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring tu hieu: SELECT * FROM products WHERE category_id = ? LIMIT ? OFFSET ?
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    // JPQL custom query
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:kw,'%'))")
    Page<Product> searchByKeyword(@Param("kw") String kw, Pageable pageable);
}
```

### 4. REST Controller
```java
@RestController           // tra ve JSON tu dong
@RequestMapping("/api/products")
public class ProductController {

    @GetMapping("/{id}")  // GET /api/products/1
    public ResponseEntity<ProductResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @PostMapping          // POST /api/products (can token)
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.status(201).body(productService.create(req));
    }
}
```

### 5. DTO Pattern — Tach request/response khoi Entity
```java
// Client gui len
ProductRequest { name, price, stock, categoryId }
    ↓ Service chuyen thanh Entity
Product { id, name, price, stock, category, createdAt, updatedAt }
    ↓ Service chuyen thanh Response
ProductResponse { id, name, price, stock, categoryId, categoryName }
    ↑ Tra ve cho client — khong lo bi lo thong tin nhay cam
```

### 6. JWT Authentication
```java
// 1. Dang nhap -> tao token
String token = jwtUtil.generateToken(user.getEmail());
// Token: eyJhbGc... (3 phan: header.payload.signature)

// 2. Moi request -> filter kiem tra
String token = header.substring(7); // bo "Bearer "
if (jwtUtil.validateToken(token)) {
    // set Authentication vao SecurityContext
}

// 3. Controller tu dong nhan biet user da dang nhap
```

### 7. Phan trang (Pagination)
```java
// GET /api/products?page=0&size=10&sortBy=price
Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
Page<Product> result = productRepository.findAll(pageable);
// Spring tu dong them: LIMIT 10 OFFSET 0 ORDER BY price
```

### 8. Global Exception Handler
```java
@RestControllerAdvice  // bat exception tu moi controller
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        // Tra ve JSON: { "status": 400, "message": "Ten khong duoc trong" }
    }
}
```

---

## Cach chay

### Yeu cau
- JDK 21
- Maven (hoac dung mvnw wrapper di kem)

### Chay
```bash
cd project-05-ecommerce-api

# Tai dependency va chay
mvn spring-boot:run
```

### Test API
Sau khi chay, mo trinh duyet:
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **H2 Console**: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:ecommercedb`)

### Luong test nhanh tren Swagger
1. `POST /api/auth/login` voi `admin@ai360.asia` / `admin123`
2. Copy token tu response
3. Click **Authorize** tren Swagger, dan token vao
4. Test cac endpoint can auth

---

## Push GitHub

```bash
git add .
git commit -m "feat: complete project 05 ecommerce REST API"
git push origin main
```

---

## So sanh voi Project 04

| | Project 04 | Project 05 |
|--|-----------|-----------|
| Giao dien | Terminal menu | HTTP REST API |
| Luu tru | File CSV | Database (H2/MySQL) |
| Chay | `java Main` | `mvn spring-boot:run` |
| Build tool | Khong co | Maven (`pom.xml`) |
| Framework | Khong co | Spring Boot |
| Auth | Khong co | JWT Token |
| Test | Nhap tay | Swagger UI / Postman |
| Query | for-each / Stream | JPA / JPQL |
| Deploy | Khong | Build jar, deploy server |
