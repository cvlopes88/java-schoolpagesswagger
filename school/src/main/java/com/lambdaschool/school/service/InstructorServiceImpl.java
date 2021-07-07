package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "instructorService")
public class InstructorServiceImpl implements InstructorService
{
    @Autowired
    private InstructorRepository instructrepos;

    @Override
    public List<Instructor> findAll() {
        List<Instructor> list = new ArrayList<>();
        instructrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Instructor save(Instructor instructor) {
        Instructor newInstructor = new Instructor();
        newInstructor.setInstructname(instructor.getInstructname());
        return instructrepos.save(newInstructor);
    }

    @Override
    public Instructor update(Instructor instructor, long id) {
        Instructor holder = instructrepos.findById(id).orElseThrow(() -> new EntityNotFoundException(Long.toString(id)));
        if(instructor.getInstructname() != null){
            holder.setInstructname(instructor.getInstructname());
        }
        return instructrepos.save(holder);
    }

    @Override
    public void delete(long id) {
        if(instructrepos.findById(id).isPresent()){
            instructrepos.deleteById(id);
        }
        else{
            throw new EntityNotFoundException(Long.toString(id));
        }
    }
}
