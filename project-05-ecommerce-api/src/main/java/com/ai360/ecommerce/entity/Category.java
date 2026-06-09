package com.ai360.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * ENTITY: Category
 * =================
 * @Entity     : class nay map voi 1 bang trong database
 * @Table      : ten bang cu the (mac dinh = ten class)
 * @Id         : khoa chinh
 * @GeneratedValue : tu dong tang ID
 *
 * @Data (Lombok): tu dong sinh getter/setter/toString/equals/hashCode
 * @NoArgsConstructor: constructor khong tham so (JPA can)
 * @AllArgsConstructor: constructor day du tham so
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    // 1 Category co nhieu Product
    // mappedBy = ten field ben Product tro ve Category
    // FetchType.LAZY: chi load products khi can (tranh N+1 query)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;
}
