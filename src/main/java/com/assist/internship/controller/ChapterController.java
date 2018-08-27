package com.assist.internship.controller;

import com.assist.internship.helpers.InternshipResponse;
import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.helpers.RoleType;
import com.assist.internship.model.Chapter;
import com.assist.internship.model.Question;
import com.assist.internship.model.User;
import com.assist.internship.repository.ChapterRepository;
import com.assist.internship.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;


    @RequestMapping(value = "/chapter", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Chapter chapter, @RequestHeader("reset_token") final String token) {

        User existUser = userService.findUserByResetToken(token);

        if(token.isEmpty() || token == null)
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));
        else {
            if (RoleType.isAdmin(existUser)) {
                Chapter newChapter = chapterService.findChapterById(chapter.getId());
                if (newChapter != null) {
                    newChapter.setContent((chapter.getContent()));
                    newChapter.setTitle(chapter.getTitle());
                    chapterService.save(newChapter);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(newChapter)));
                } else
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided id doesn't belong to any existing chapter.", null));
            }else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
        }
    }

    @RequestMapping(value = "/create/chapter", method = RequestMethod.POST)
    public ResponseEntity createNewChapter(@RequestBody Chapter chapter, @RequestHeader("reset_token") final String token, @RequestParam("course") final int id) {


        User user = userService.findUserByResetToken(token);

        Chapter dbChapter = chapterService.findChapterByTitle(chapter.getTitle());
        String title = chapter.getTitle();

        if (courseService.findCourseById(id) != null) {
            if (RoleType.isAdmin(user)) {
                if (token.isEmpty() || token == null)
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access denied, please login!", null));
                else {
                    if (chapter.getTitle() != null) {
                        if (title.isEmpty())
                            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The supplied chapter title is not valid!", null));
                        else {
                            if (dbChapter != null)
                                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The selected chapter title already belongs to an chapter. Please use a different one!", null));
                            else {
                                chapter.setCourse(courseService.findCourseById(id));
                                chapterRepository.save(chapter);
                                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Chapter created successfully!", Arrays.asList(chapter)));
                            }
                        }
                    } else
                        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Please fill in all the mandatory fields to successfully create the chapter!", null));
                }
            } else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
        } else
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "No category with ID " + id + " found!", null));
    }


    //show all chapters by course id
    @RequestMapping(value = "/chapters", method = RequestMethod.GET)
    public ResponseEntity Chapters(@RequestHeader("reset_token") final String token, @RequestParam("course_id") final int id){

        if(token.isEmpty() || token == null)
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));
        }
        else
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Found all chapters", chapterService.findChapterByCourseId(id)));
        }
    }

    @RequestMapping(value = "/chapter", method = RequestMethod.GET)
    public ResponseEntity Chapter(@RequestParam("id") final int id, @RequestHeader("reset_token") final String token){
        if(token.isEmpty() || token == null){
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));
        }
        else{
            Chapter findChapter = chapterService.findChapterById(id);
            if (id > 1) {
                if(findChapter!=null) {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(findChapter)));
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided id doesn't belong to any existing chapter.", null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InternshipResponse(false, "Please provide a valid chapter id.", null));
            }
        }
    }





}