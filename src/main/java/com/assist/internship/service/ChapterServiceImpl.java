package com.assist.internship.service;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Chapter;
import com.assist.internship.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChapterServiceImpl implements ChapterService{

    @Autowired
    private ChapterRepository chapterRepository;

    @Override
    public Chapter save(Chapter chapter) {

        return chapterRepository.save(chapter);
    }


    @Override
    public List<ResponseObject> findChapterByCourseId(int idCourse){

        return chapterRepository.findByCourseId(idCourse);
    }

    @Override
    public Chapter findChapterById(int id) {

        return chapterRepository.findById(id);
    }

    @Override
    public Chapter findChapterByTitle(String title){
        return chapterRepository.findByTitle(title);
    }

}