package com.assist.internship.service;

import com.assist.internship.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    public Category findByCategoryName(String name);
    public Category findByCategoryId(int id);
    public void save(Category category);
    public void delete(Category category);
    public List<Category> findAll();
}
