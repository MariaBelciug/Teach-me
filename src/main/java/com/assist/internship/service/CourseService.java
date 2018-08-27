package com.assist.internship.service;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CourseService
{
    public Course findCourseById(int id);
    public List<ResponseObject> findCourseByCategoryId(int id);
    public Course findCourseBySmallDescription(String description);
    public void saveCourse(Course course);
    public void deleteCourse(Course course);
    public List<Course> findAll(Iterable<Long> id);
}
