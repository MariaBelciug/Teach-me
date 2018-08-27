package com.assist.internship.repository;

;
import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findByName(String question);

    List<ResponseObject> findByChapterId(int id);

    Question findById(int id);
}