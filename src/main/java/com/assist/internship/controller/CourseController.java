package com.assist.internship.controller;

import com.assist.internship.helpers.InternshipResponse;
import com.assist.internship.helpers.RoleType;
import com.assist.internship.model.Course;
import com.assist.internship.model.User;
import com.assist.internship.service.CategoryService;
import com.assist.internship.service.CourseService;
import com.assist.internship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CourseController
{
    @Autowired
    private CourseService courseService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "/course", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Course course, @RequestHeader("reset_token") final String token) {

        if(token.isEmpty() || token == null)
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));
        else {
            User userAdmin = userService.findUserByResetToken(token);

            if (RoleType.isAdmin(userAdmin) == Boolean.TRUE) {
                Course newCourse = courseService.findCourseById(course.getId());
                if (newCourse != null) {
                    newCourse.setImages(course.getImages());
                    newCourse.setSmallDescription((course.getSmallDescription()));
                    newCourse.setLongDescription(course.getLongDescription());
                    newCourse.setTags(course.getTags());
                    courseService.saveCourse(newCourse);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(newCourse)));
                } else
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided id doesn't belong to any existing course.", null));
            }else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
        }
    }

    // /courses?category=4 GET all courses of a specified CATEGORY Id
    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public ResponseEntity getCourses(@RequestHeader("reset_token") final String token, @RequestParam("category") final int id) {
        if(token.equals("") || token == null)
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));
        else {
            Course oldCourse = new Course();
            oldCourse.setCategory(categoryService.findByCategoryId(id));
            if(oldCourse.getCategory() != null)
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Found!", courseService.findCourseByCategoryId(id)));
            else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Category not found!", null));
        }
    }

    @RequestMapping(value = "/course", method = RequestMethod.GET)
    public ResponseEntity getCourse(@RequestHeader("reset_token") final String token, @RequestParam("id") final int id) {
        if(token.equals("") || token == null)
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));
        else {
            Course oldCourse = courseService.findCourseById(id);
            if(oldCourse != null)
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Found!", Arrays.asList(oldCourse)));
            else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Course Not Found!", null));
        }
    }

    @RequestMapping(value = "/create/course", method = RequestMethod.POST)
    public ResponseEntity createCourse(@RequestHeader("reset_token") final String token, @RequestBody Course course, @RequestParam("category") final int id) {

        User existUser = userService.findUserByResetToken(token);

        if(categoryService.findByCategoryId(id) != null){
        if (RoleType.isAdmin(existUser)) {
            if (token.equals("") || token == null) {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));
            } else {
                Course oldCourse = courseService.findCourseBySmallDescription(course.getSmallDescription());
                if (oldCourse == null) {
                    course.setCategory(categoryService.findByCategoryId(id));
                    courseService.saveCourse(course);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(course)));
                } else
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "This Course already exists, please make a new Course", null));
            }
        }else
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
        }else
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "No category with ID " + id + " found!", null));
    }

    @RequestMapping(value = "course", method = RequestMethod.DELETE)
    public ResponseEntity deleteCourse(@RequestHeader("reset_token") final String token, @RequestBody Course course) {

        User existUser = userService.findUserByResetToken(token);
            if (RoleType.isAdmin(existUser)) {
                if (token.equals("") || token == null) {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));
                } else {
                    Course oldCourse = courseService.findCourseBySmallDescription(course.getSmallDescription());
                    if (oldCourse != null) {
                        courseService.deleteCourse(course);
                        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Deleted", Arrays.asList(course)));
                    } else
                        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "This Course does not exist!", null));
                }
            }else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
    }
}
