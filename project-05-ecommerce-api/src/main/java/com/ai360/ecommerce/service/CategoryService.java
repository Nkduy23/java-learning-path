package com.ai360.ecommerce.service;

import com.ai360.ecommerce.dto.Dto.*;
import com.ai360.ecommerce.entity.Category;
import com.ai360.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category khong ton tai: " + id));
    }

    @Transactional
    public Category create(CategoryRequest req) {
        if (categoryRepository.existsByName(req.getName())) {
            throw new RuntimeException("Category da ton tai: " + req.getName());
        }
        Category cat = new Category();
        cat.setName(req.getName());
        cat.setDescription(req.getDescription());
        return categoryRepository.save(cat);
    }

    @Transactional
    public Category update(Long id, CategoryRequest req) {
        Category cat = getById(id);
        cat.setName(req.getName());
        cat.setDescription(req.getDescription());
        return categoryRepository.save(cat);
    }

    @Transactional
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) throw new RuntimeException("Category khong ton tai: " + id);
        categoryRepository.deleteById(id);
    }
}
