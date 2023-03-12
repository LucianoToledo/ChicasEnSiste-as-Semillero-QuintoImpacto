package com.chicasensistemas.service.abstraction;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.entity.CourseEntity;
import com.chicasensistemas.model.entity.ModuleEntity;
import com.chicasensistemas.model.request.DeleteVideoByModuleRequest;
import com.chicasensistemas.model.request.ModuleRequest;
import com.chicasensistemas.model.request.UpdateModuleRequest;
import com.chicasensistemas.model.request.UpdateVideoIdRequest;
import com.chicasensistemas.model.response.ListModuleResponse;
import com.chicasensistemas.model.response.ModuleResponse;

import java.util.List;

public interface IModuleService {

    ModuleResponse create(ModuleRequest moduleRequest) throws Exception;

    ModuleResponse update(UpdateModuleRequest moduleRequest);

    void disableModule (String id) throws EntityNotFoundException;

    void enableModule (String id) throws EntityNotFoundException;

    ModuleResponse getByIdResponse (String id) throws EntityNotFoundException;

    ListModuleResponse getModules() throws EntityNotFoundException;

    ModuleResponse removeVideoFromModule (DeleteVideoByModuleRequest request);

    ModuleEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException;

    void updateConnected(ModuleEntity entity) throws Exception;

    ModuleResponse addVideo(UpdateVideoIdRequest request) throws Exception;

    List<ModuleEntity> getModuleListByCourseId(String idCourse);

    void removeModuleCourse(ModuleEntity moduleEntity, CourseEntity courseEntity);

    String getModuleName(String idModule);

    String getCourseByModuleId (String moduleId);
}
