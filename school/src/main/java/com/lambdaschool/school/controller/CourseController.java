package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.CourseService;
import com.lambdaschool.school.view.CountStudentsInCourses;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/courses")
public class CourseController
{
    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "returns all courses available", response = Course.class, responseContainer = "List")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integr", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})



    //http://localhost:2019/courses/courses
    //http://localhost:2019/courses/courses/paging/?page=1
    @GetMapping(value = "/courses/paging", produces = {"application/json"})
    public ResponseEntity<?> listAllCoursesByPage(@PageableDefault(page = 0, size = 3) Pageable pageable){
        List<Course> myCourses = courseService.findAllPageable(pageable);
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }


    @ApiOperation(value ="Returns all Instructors", response = Course.class, responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of Courses Found", response = Course.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "List of Courses Not Found")})
    //http://localhost:2019/courses/courses/?sort=coursename,desc
    @GetMapping(value = "/courses", produces = {"application/json"})
    public ResponseEntity<?> listAllCourses()
    {
        ArrayList<Course> myCourses = courseService.findAll();
        return new ResponseEntity<>(myCourses, HttpStatus.OK);
    }

    @ApiOperation(value ="Returns Count of Students in Each Course", responseContainer = "List")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List Found",  responseContainer = "List"),
            @ApiResponse(code = 404, message = "List Not Found")})
    @GetMapping(value = "/studcount", produces = {"application/json"})
    public ResponseEntity<?> getCountStudentsInCourses()
    {
        return new ResponseEntity<>(courseService.getCountStudentsInCourse(), HttpStatus.OK);
    }

    @ApiOperation(value ="Deletes Course Depending on CourseId")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Course Deleted"),
            @ApiResponse(code = 404, message = "Course Not Deleted")})
    @DeleteMapping("/courses/{courseid}")
    public ResponseEntity<?> deleteCourseById(@PathVariable long courseid)
    {
        courseService.delete(courseid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
