package com.assist.internship.controller;

import com.assist.internship.helpers.InternshipResponse;
import com.assist.internship.helpers.RoleType;
import com.assist.internship.model.Answer;
import com.assist.internship.model.User;
import com.assist.internship.service.AnswerService;
import com.assist.internship.service.QuestionService;
import com.assist.internship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/answer", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Answer answer, @RequestHeader("reset_token") final String token) {

        User oldUser = userService.findUserByResetToken(token);

        if(token.isEmpty() || token == null)
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));
        else {
            if (RoleType.isAdmin(oldUser)) {

                Answer newAnswer = answerService.findAnswerById(answer.getId());
                if (newAnswer != null) {
                    newAnswer.setContent((answer.getContent()));
                    newAnswer.setIs_correct(answer.isIs_correct());
                    answerService.saveAnswer(newAnswer);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(newAnswer)));
                } else
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided id doesn't belong to any existing answer.", null));

            }else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
        }
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ResponseEntity getAnswer(@RequestParam("question")final int id,@RequestHeader("reset_token") final String token){
        if(token!=null) {

            if (StringUtils.isEmpty(id))
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Please enter id ", null));
             else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Answer for specified question id !", answerService.findAnswerByQuestionId(id)));
        }else
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied! ", null));
    }

    @RequestMapping(value = "/create/answer",method = RequestMethod.POST)
    public ResponseEntity createAnswer(@RequestBody Answer answer,@RequestHeader("reset_token") final String token, @RequestParam("chapter") final int id){

        User oldUser = userService.findUserByResetToken(token);
        if(questionService.findQuestionById(id) != null) {
            if (RoleType.isAdmin(oldUser)) {
                if (token.isEmpty() || token == null) {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));
                } else {
                    answer.setQuestion(questionService.findQuestionById(id));
                    answerService.saveAnswer(answer);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(answer)));
                }
            } else
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "You are not authorized to perform this operation!", null));
        }else
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "No question with ID " + id + " found!", null));
    }

}