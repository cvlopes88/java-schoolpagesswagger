package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Instructor;

import java.util.List;

public interface InstructorService
{


    List<Instructor> findAll();

    Instructor save(Instructor instructor);

    Instructor update(Instructor instructor, long id);

    void delete(long id);
}
