package com.ai360.ecommerce.service;

import com.ai360.ecommerce.dto.Dto.*;
import com.ai360.ecommerce.entity.*;
import com.ai360.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private ProductResponse toResponse(Product p) {
        return new ProductResponse(
            p.getId(), p.getName(), p.getDescription(),
            p.getPrice(), p.getStock(), p.getImageUrl(),
            p.getCategory().getId(), p.getCategory().getName()
        );
    }

    public PageResponse<ProductResponse> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Product> result = productRepository.findAll(pageable);
        List<ProductResponse> content = result.getContent().stream().map(this::toResponse).collect(Collectors.toList());
        return new PageResponse<>(content, page, size, result.getTotalElements(), result.getTotalPages());
    }

    public ProductResponse getById(Long id) {
        return toResponse(productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product khong ton tai: " + id)));
    }

    public PageResponse<ProductResponse> search(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> result = productRepository.searchByKeyword(keyword, pageable);
        List<ProductResponse> content = result.getContent().stream().map(this::toResponse).collect(Collectors.toList());
        return new PageResponse<>(content, page, size, result.getTotalElements(), result.getTotalPages());
    }

    public PageResponse<ProductResponse> getByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> result = productRepository.findByCategoryId(categoryId, pageable);
        List<ProductResponse> content = result.getContent().stream().map(this::toResponse).collect(Collectors.toList());
        return new PageResponse<>(content, page, size, result.getTotalElements(), result.getTotalPages());
    }

    @Transactional
    public ProductResponse create(ProductRequest req) {
        Category cat = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category khong ton tai: " + req.getCategoryId()));
        Product p = new Product();
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setStock(req.getStock());
        p.setImageUrl(req.getImageUrl());
        p.setCategory(cat);
        return toResponse(productRepository.save(p));
    }

    @Transactional
    public ProductResponse update(Long id, ProductRequest req) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product khong ton tai: " + id));
        Category cat = categoryRepository.findById(req.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category khong ton tai: " + req.getCategoryId()));
        p.setName(req.getName());
        p.setDescription(req.getDescription());
        p.setPrice(req.getPrice());
        p.setStock(req.getStock());
        p.setImageUrl(req.getImageUrl());
        p.setCategory(cat);
        return toResponse(productRepository.save(p));
    }

    @Transactional
    public void delete(Long id) {
        if (!productRepository.existsById(id)) throw new RuntimeException("Product khong ton tai: " + id);
        productRepository.deleteById(id);
    }
}
