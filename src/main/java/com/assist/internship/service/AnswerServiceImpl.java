package com.assist.internship.service;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Answer;
import com.assist.internship.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService{
    @Autowired
    private AnswerRepository answerRepository;

    @Override
    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }
    @Override
    public List<ResponseObject> findAnswerByQuestionId(int id)
    {
        return answerRepository.findByQuestionId(id);
    }
    @Override
    public Answer findAnswerById(int id)
    {
        return answerRepository.findById(id);
    }
}