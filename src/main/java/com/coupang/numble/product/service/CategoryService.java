package com.coupang.numble.product.service;

import com.coupang.numble.product.entity.Category;
import com.coupang.numble.product.repository.CategoryRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAllRootCategory(Long categoryId) {
        List<Category> categoryList = new ArrayList<>();
        Category category = repository.findById(categoryId).orElse(null);
        if (category == null)
            return categoryList;
        categoryList.add(category);
        while (category.getParent() != null) {
            category = repository.findById(category.getParent().getId()).orElse(null);
            if (category == null)
                break;
            categoryList.add(category);
        }
        Collections.reverse(categoryList);
        return categoryList;
    }

    public List<Category> getChildCategoryId(Long categoryId) {
        Queue<Category> categoryQueue = new LinkedList<>();
        categoryQueue.addAll(repository.findAllByParentId(categoryId));
        List<Category> categoryList = new ArrayList<>();
        if (categoryQueue.isEmpty())
            categoryList.add(repository.findById(categoryId).orElse(null));
        while (!categoryQueue.isEmpty()) {
            Category category = categoryQueue.poll();
            List<Category> tmp = repository.findAllByParentId(category.getId());
            if (tmp.size() == 0)
                categoryList.add(category);
            else
                categoryQueue.addAll(tmp);
        }
        return categoryList;
    }

}
