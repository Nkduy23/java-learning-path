package com.ai360.ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * ENTITY: User
 * =============
 * Nguoi dung he thong. Co 2 role: USER va ADMIN.
 *
 * @Enumerated(STRING): luu role la chuoi "USER"/"ADMIN" trong DB
 * thay vi so 0/1 (de doc hon khi xem DB truc tiep)
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String fullName;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password; // da duoc hash bang BCrypt

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role = Role.USER;

    // Enum dinh nghia cac role hop le
    public enum Role {
        USER, ADMIN
    }
}
