package com.assist.internship.service;

import com.assist.internship.model.Category;
import com.assist.internship.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


   @Override
    public Category findByCategoryName(String name) {
    return categoryRepository.findByName(name);
    }

    @Override
    public Category findByCategoryId(int id) {
        return categoryRepository.findById(id);
    }


    @Override
    public void save(Category category){ categoryRepository.save(category); }

    @Override
    public void delete(Category category){ categoryRepository.delete(category);}

    @Override
    public List<Category> findAll() {
       return categoryRepository.findAll();
    }
}
