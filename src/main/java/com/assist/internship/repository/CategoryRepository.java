package com.assist.internship.repository;

import com.assist.internship.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findById(int id);
    Category findByName(String categoryName);
    Category save(Category category);
    List<Category> findAll();
}
