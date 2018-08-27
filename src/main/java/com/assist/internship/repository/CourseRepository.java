package com.assist.internship.repository;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>
{
    // Course findBySmall_description(String description);
    Course findById(int id);
    Course findBySmallDescription(String description);
    List<ResponseObject> findByCategoryId(int id);
}

