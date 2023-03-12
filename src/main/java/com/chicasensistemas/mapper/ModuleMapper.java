package com.chicasensistemas.mapper;

import com.chicasensistemas.model.entity.ModuleEntity;
import com.chicasensistemas.model.request.ModuleToCourseRequest;
import com.chicasensistemas.model.request.ModuleRequest;
import com.chicasensistemas.model.response.ModuleResponse;
import com.chicasensistemas.service.CourseServiceImpl;
import com.chicasensistemas.service.ModuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ModuleMapper {

    @Autowired
    private VideoMapper videoMapper;

    @Lazy
    @Autowired
    ModuleServiceImpl moduleService;

    @Lazy
    @Autowired
    CourseServiceImpl courseService;

    public ModuleEntity map(ModuleRequest request) {
        ModuleEntity entity = new ModuleEntity();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        return entity;
    }

    public ModuleResponse map(ModuleEntity moduleEntity) {
        ModuleResponse moduleResponse = new ModuleResponse();
        moduleResponse.setId(moduleEntity.getModuleId());
        moduleResponse.setName(moduleEntity.getName());
        moduleResponse.setDescription(moduleEntity.getDescription());
        moduleResponse.setSoftDelete(moduleEntity.getSoftDelete());
        moduleResponse.setConnected(moduleEntity.getConnected());
        if (moduleEntity.getVideosCourse() != null) {
            moduleResponse.setVideosCourse(videoMapper.map(moduleEntity.getVideosCourse()));
        }
        return moduleResponse;
    }

    public List<ModuleResponse> map(List<ModuleEntity> list) {
        List<ModuleResponse> moduleResponses = new ArrayList<>(list.size());
        for (ModuleEntity module : list) {
            ModuleResponse response = map(module);
            String idCourse = moduleService.getCourseByModuleId(module.getModuleId());
            response.setIdCourse(idCourse != null ? idCourse : null);
            response.setCourseName( idCourse != null ? courseService.getCourseName(idCourse) : null);
            moduleResponses.add(response);
        }
        return moduleResponses;
    }

    public ModuleToCourseRequest map(String idModule, String idCourse) {
        ModuleToCourseRequest request = new ModuleToCourseRequest();
        request.setIdModule(idModule);
        request.setIdCourse(idCourse);
        return request;
    }
}
