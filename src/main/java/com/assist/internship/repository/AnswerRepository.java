package com.assist.internship.repository;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {

    List<ResponseObject> findByQuestionId(int id);
    Answer findById(int id);


}