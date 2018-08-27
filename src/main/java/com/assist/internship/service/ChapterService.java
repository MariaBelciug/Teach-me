package com.assist.internship.service;

import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.model.Chapter;

import java.util.List;

public interface ChapterService {

    public Chapter save(Chapter chapter);

    public List<ResponseObject> findChapterByCourseId(int idCourse);

    public Chapter findChapterById(int id);

    public Chapter findChapterByTitle(String title);
}