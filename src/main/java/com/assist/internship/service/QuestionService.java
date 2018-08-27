package com.assist.internship.service;


import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Question;
import org.springframework.stereotype.Service;

import java.util.List;


public interface QuestionService {

    public void saveQuestion(Question question);
    public Question findQuestionByName (String question);
    List<ResponseObject> findQuestionByChapterId(int id);
    public Question findQuestionById(int id);
}