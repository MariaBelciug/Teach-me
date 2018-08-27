package com.assist.internship.service;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Course;
import com.assist.internship.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService
{
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public Course findCourseById(int id)
    {
        return courseRepository.findById(id);
    }

    @Override
    public Course findCourseBySmallDescription(String description)
    {
        return courseRepository.findBySmallDescription(description);
    }

    @Override
    public List<ResponseObject> findCourseByCategoryId(int id)
    {
        return courseRepository.findByCategoryId(id);
    }
    @Override
    public void saveCourse(Course course)
    {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Course course)
    {
        courseRepository.delete(course);
    }

    @Override
    public List<Course> findAll(Iterable<Long> id)
    {
        return courseRepository.findAllById(id);
    }

}
