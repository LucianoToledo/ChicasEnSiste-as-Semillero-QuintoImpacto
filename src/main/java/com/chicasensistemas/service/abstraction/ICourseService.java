package com.chicasensistemas.service.abstraction;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.entity.CourseEntity;
import com.chicasensistemas.model.request.CourseRequest;
import com.chicasensistemas.model.request.ModuleToCourseRequest;
import com.chicasensistemas.model.request.UpdateCourseRequest;
import com.chicasensistemas.model.response.CourseResponse;
import com.chicasensistemas.model.response.ListCourseResponse;

public interface ICourseService {
    CourseResponse createCourse(CourseRequest request) throws Exception;

    CourseResponse enableCourse(String id) throws EntityNotFoundException;

    CourseResponse disableCourse(String id) throws EntityNotFoundException;

    CourseResponse updateCourse(UpdateCourseRequest request);

    ListCourseResponse getCourses() throws EntityNotFoundException;

    CourseResponse getByNameAndSoftDeleteFalse(String name) throws EntityNotFoundException;

    CourseResponse getByIdResponse(String id) throws EntityNotFoundException;

    CourseEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException;

    CourseResponse addModule(ModuleToCourseRequest request) throws Exception;

    CourseResponse removeModule(ModuleToCourseRequest request);

    String getCourseName(String idCourse);
}
