package com.assist.internship.repository;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long>{

    Chapter findById(int idChapter);

    List<ResponseObject> findByCourseId(int idCourse);

    Chapter findByTitle(String title);
}