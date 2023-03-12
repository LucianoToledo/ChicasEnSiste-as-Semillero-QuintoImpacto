package com.chicasensistemas.mapper;

import com.chicasensistemas.model.entity.CourseEntity;
import com.chicasensistemas.model.request.CourseRequest;
import com.chicasensistemas.model.response.CourseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CourseMapper {

    @Autowired
    ModuleMapper moduleMapper;



    public CourseResponse map(CourseEntity course) {
        CourseResponse courseResponse = new CourseResponse();
        courseResponse.setId(course.getCourseId());
        courseResponse.setNameCourse(course.getNameCourse());
        courseResponse.setDescription(course.getDescription());
        courseResponse.setPrice(course.getPrice());
        courseResponse.setDuration(course.getDuration());
        courseResponse.setSoftDelete(course.getSoftDelete());
        if (course.getModules() != null) {
            courseResponse.setModules(moduleMapper.map(course.getModules())); //TODO si cargo un module sin video, tirará error??
        }
        return courseResponse;
    }

    public CourseEntity map(CourseRequest request) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setNameCourse(request.getNameCourse());
        courseEntity.setDescription(request.getDescription());
        courseEntity.setPrice(request.getPrice());
        courseEntity.setDuration(request.getDuration());
        return courseEntity;
    }

    public List<CourseResponse> map(List<CourseEntity> courses) {
        List<CourseResponse> courseResponses = new ArrayList<>(courses.size());
        for (CourseEntity course : courses) {
            courseResponses.add(map(course));
        }
        return courseResponses;
    }

}
