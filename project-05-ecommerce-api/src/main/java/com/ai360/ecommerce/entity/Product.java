package com.ai360.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * ENTITY: Product
 * ================
 * Kien thuc:
 * - @ManyToOne: nhieu Product thuoc 1 Category
 * - @JoinColumn: ten cot foreign key trong bang products
 * - @Column constraints: nullable, unique, length
 * - @CreationTimestamp: tu dong set thoi gian tao
 * - BigDecimal cho gia tien (chinh xac hon double)
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ten san pham khong duoc trong")
    @Column(nullable = false, length = 200)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Gia khong duoc null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Gia phai lon hon 0")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal price;

    @Min(value = 0, message = "So luong khong duoc am")
    @Column(nullable = false)
    private Integer stock = 0;

    @Column(name = "image_url")
    private String imageUrl;

    // ManyToOne: nhieu Product -> 1 Category
    // @JoinColumn: ten cot FK la "category_id"
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Tu dong set thoi gian truoc khi luu lan dau
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    // Tu dong cap nhat thoi gian moi khi update
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
