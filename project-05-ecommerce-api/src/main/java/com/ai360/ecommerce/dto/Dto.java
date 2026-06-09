package com.ai360.ecommerce.dto;

import com.ai360.ecommerce.entity.User;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

public class Dto {

    public static class RegisterRequest {
        @NotBlank(message = "Ho ten khong duoc trong")
        private String fullName;
        @Email(message = "Email khong hop le") @NotBlank
        private String email;
        @NotBlank @Size(min = 6, message = "Mat khau toi thieu 6 ky tu")
        private String password;

        public String getFullName() { return fullName; }
        public String getEmail()    { return email; }
        public String getPassword() { return password; }
        public void setFullName(String v) { this.fullName = v; }
        public void setEmail(String v)    { this.email = v; }
        public void setPassword(String v) { this.password = v; }
    }

    public static class LoginRequest {
        @Email @NotBlank private String email;
        @NotBlank         private String password;

        public String getEmail()    { return email; }
        public String getPassword() { return password; }
        public void setEmail(String v)    { this.email = v; }
        public void setPassword(String v) { this.password = v; }
    }

    public static class AuthResponse {
        private String token;
        private String type = "Bearer";
        private String email;
        private String fullName;
        private User.Role role;

        public AuthResponse(String token, String email, String fullName, User.Role role) {
            this.token = token; this.email = email;
            this.fullName = fullName; this.role = role;
        }
        public String getToken()    { return token; }
        public String getType()     { return type; }
        public String getEmail()    { return email; }
        public String getFullName() { return fullName; }
        public User.Role getRole()  { return role; }
    }

    public static class CategoryRequest {
        @NotBlank(message = "Ten danh muc khong duoc trong")
        private String name;
        private String description;

        public String getName()        { return name; }
        public String getDescription() { return description; }
        public void setName(String v)        { this.name = v; }
        public void setDescription(String v) { this.description = v; }
    }

    public static class ProductRequest {
        @NotBlank private String name;
        private String description;
        @NotNull @DecimalMin("0.01") private BigDecimal price;
        @Min(0) private Integer stock = 0;
        private String imageUrl;
        @NotNull private Long categoryId;

        public String getName()        { return name; }
        public String getDescription() { return description; }
        public BigDecimal getPrice()   { return price; }
        public Integer getStock()      { return stock; }
        public String getImageUrl()    { return imageUrl; }
        public Long getCategoryId()    { return categoryId; }
        public void setName(String v)        { this.name = v; }
        public void setDescription(String v) { this.description = v; }
        public void setPrice(BigDecimal v)   { this.price = v; }
        public void setStock(Integer v)      { this.stock = v; }
        public void setImageUrl(String v)    { this.imageUrl = v; }
        public void setCategoryId(Long v)    { this.categoryId = v; }
    }

    public static class ProductResponse {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private Integer stock;
        private String imageUrl;
        private Long categoryId;
        private String categoryName;

        public ProductResponse(Long id, String name, String description, BigDecimal price,
                               Integer stock, String imageUrl, Long categoryId, String categoryName) {
            this.id = id; this.name = name; this.description = description;
            this.price = price; this.stock = stock; this.imageUrl = imageUrl;
            this.categoryId = categoryId; this.categoryName = categoryName;
        }
        public Long getId()             { return id; }
        public String getName()         { return name; }
        public String getDescription()  { return description; }
        public BigDecimal getPrice()    { return price; }
        public Integer getStock()       { return stock; }
        public String getImageUrl()     { return imageUrl; }
        public Long getCategoryId()     { return categoryId; }
        public String getCategoryName() { return categoryName; }
    }

    public static class PageResponse<T> {
        private List<T> content;
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;

        public PageResponse(List<T> content, int page, int size, long totalElements, int totalPages) {
            this.content = content; this.page = page; this.size = size;
            this.totalElements = totalElements; this.totalPages = totalPages;
        }
        public List<T> getContent()      { return content; }
        public int getPage()             { return page; }
        public int getSize()             { return size; }
        public long getTotalElements()   { return totalElements; }
        public int getTotalPages()       { return totalPages; }
    }

    public static class ErrorResponse {
        private int status;
        private String message;
        private long timestamp;

        public ErrorResponse(int status, String message) {
            this.status = status; this.message = message;
            this.timestamp = System.currentTimeMillis();
        }
        public int getStatus()      { return status; }
        public String getMessage()  { return message; }
        public long getTimestamp()  { return timestamp; }
    }
}
