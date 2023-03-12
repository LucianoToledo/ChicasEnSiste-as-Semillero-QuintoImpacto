package com.chicasensistemas.controller;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.request.CourseRequest;
import com.chicasensistemas.model.request.ModuleToCourseRequest;
import com.chicasensistemas.model.request.NameCourseRequest;
import com.chicasensistemas.model.request.UpdateCourseRequest;
import com.chicasensistemas.model.response.CourseResponse;
import com.chicasensistemas.model.response.ListCourseResponse;
import com.chicasensistemas.service.abstraction.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private ICourseService iCourseService;

    @PostMapping("/create")
    public ResponseEntity<CourseResponse> create(@RequestBody CourseRequest request) {
        try {
            CourseResponse response = iCourseService.createCourse(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<CourseResponse> disableCourse(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(iCourseService.disableCourse(id));
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<CourseResponse> enableCourse(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(iCourseService.enableCourse(id));
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<Void> update(@RequestBody UpdateCourseRequest request) {
        iCourseService.updateCourse(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list")
    public ResponseEntity<ListCourseResponse> getCourses() throws EntityNotFoundException {
        return ResponseEntity.ok().body(iCourseService.getCourses());
    }

    @GetMapping("/getByName")
    public ResponseEntity<CourseResponse> getByName(@RequestBody NameCourseRequest request) throws EntityNotFoundException {
        return ResponseEntity.ok().body(iCourseService.getByNameAndSoftDeleteFalse(request.getName()));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<CourseResponse> getById(@PathVariable String id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(iCourseService.getByIdResponse(id));
    }

    @PutMapping("/addModule")
    public ResponseEntity<CourseResponse> addModule(@RequestBody ModuleToCourseRequest request) {
        try {
            return ResponseEntity.ok().body(iCourseService.addModule(request));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/removeModule")
    public ResponseEntity<CourseResponse> removeModule(@RequestBody ModuleToCourseRequest request) {
        return ResponseEntity.ok().body(iCourseService.removeModule(request));
    }
}
