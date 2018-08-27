package com.assist.internship.controller;


import com.assist.internship.helpers.InternshipResponse;
import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.helpers.RoleType;
import com.assist.internship.model.Chapter;
import com.assist.internship.model.Question;
import com.assist.internship.model.User;
import com.assist.internship.service.AnswerService;
import com.assist.internship.service.ChapterService;
import com.assist.internship.service.QuestionService;
import com.assist.internship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private ChapterService chapterService;

    //GET   /questions?chapter=4 - get all the questions for a specified chapter id
    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public ResponseEntity questions (@RequestParam("id")final int id, @RequestHeader("reset_token") final String token){
        if(token!=null) {
            if (StringUtils.isEmpty(id))
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Please enter id ", null));
             else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Question for specified chapter id !", questionService.findQuestionByChapterId(id)));
        }else
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied! ", null));
    }

    @RequestMapping(value = "/question", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Question question, @RequestHeader("reset_token") final String token) {

        User oldUser = userService.findUserByResetToken(token);

        if (token.isEmpty() || token == null)
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));
        else {
            if (RoleType.isAdmin(oldUser)) {
                Question newQuestion = questionService.findQuestionById(question.getId());
                if (newQuestion != null) {
                    newQuestion.setContent((question.getContent()));
                    newQuestion.setName(question.getName());
                    questionService.saveQuestion(newQuestion);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(newQuestion)));
                } else
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided id doesn't belong to any existing question.", null));
            }else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
        }
    }

    @RequestMapping(value = "/create/question", method = RequestMethod.POST)
    public ResponseEntity createQuestion (@RequestBody Question question, @RequestHeader("reset_token") final String token, @RequestParam("chapter") final int id){

        User oldUser = userService.findUserByResetToken(token);

        if(chapterService.findChapterById(id) != null) {
            if (RoleType.isAdmin(oldUser)) {
                if (token != null) {
                    Question dbquestion = questionService.findQuestionByName(question.getName());
                    if (dbquestion == null) {
                        question.setChapter(chapterService.findChapterById(id));
                        questionService.saveQuestion(question);
                        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Question created successfully!", Arrays.asList(question)));
                    } else
                        return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "This question exist ", null));
                } else
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied! ", null));
            } else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
        }else
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "No chapter with ID " + id + " found!", null));
    }
}

