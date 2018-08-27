package com.assist.internship.service;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Answer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {

    public void saveAnswer(Answer answer);
    public Answer findAnswerById(int id);
    List<ResponseObject> findAnswerByQuestionId(int id);
}