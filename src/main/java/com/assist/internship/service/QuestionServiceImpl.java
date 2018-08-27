package com.assist.internship.service;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Question;
import com.assist.internship.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Autowired
    private QuestionRepository questionRepository;


    @Override
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question findQuestionByName(String question)
    {
        return questionRepository.findByName(question);
    }


    @Override
    public List<ResponseObject> findQuestionByChapterId(int id)
    {
        return questionRepository.findByChapterId(id);
    }

    @Override
    public Question findQuestionById(int id){
        return questionRepository.findById(id);
    }
}