package com.chicasensistemas.service;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.mapper.ModuleMapper;
import com.chicasensistemas.model.entity.CourseEntity;
import com.chicasensistemas.model.entity.ModuleEntity;
import com.chicasensistemas.model.entity.VideoEntity;
import com.chicasensistemas.model.request.DeleteVideoByModuleRequest;
import com.chicasensistemas.model.request.ModuleRequest;
import com.chicasensistemas.model.request.UpdateModuleRequest;
import com.chicasensistemas.model.request.UpdateVideoIdRequest;
import com.chicasensistemas.model.response.ListModuleResponse;
import com.chicasensistemas.model.response.ModuleResponse;
import com.chicasensistemas.repository.IModuleRepository;
import com.chicasensistemas.service.abstraction.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ModuleServiceImpl implements IModuleService {

    @Autowired
    IModuleRepository moduleRepository;
    @Autowired
    ModuleMapper moduleMapper;
    @Autowired
    VideoServiceImpl videoService;
    @Lazy
    @Autowired
    CourseServiceImpl courseService;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ModuleResponse create(ModuleRequest moduleRequest) throws Exception {
        ModuleEntity module = moduleMapper.map(moduleRequest);
        ModuleResponse response = moduleMapper.map(moduleRepository.save(module));
        if (!moduleRequest.getIdCourse().isEmpty()) {
            courseService.addModule(moduleMapper.map(response.getId(), moduleRequest.getIdCourse()));
            module.setConnected(true);
            response = moduleMapper.map(moduleRepository.save(module));
            response.setIdCourse(moduleRequest.getIdCourse());
            response.setCourseName(courseService.getCourseName(moduleRequest.getIdCourse()));
        }
        return response;
    }


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ModuleResponse update(UpdateModuleRequest moduleRequest) {
        ModuleEntity entity = getById(moduleRequest.getId());
        entity.setName(moduleRequest.getName());
        entity.setDescription(moduleRequest.getDescription());
        return moduleMapper.map(moduleRepository.save(entity));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void disableModule(String id) throws EntityNotFoundException {
        ModuleEntity module = getById(id);
        if (!module.isEnabled()) {
            throw new EntityNotFoundException("MODULE IS ALREADY DISABLE");
        }
        module.setSoftDelete(true);
        moduleRepository.save(module);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void enableModule(String id) throws EntityNotFoundException {
        ModuleEntity module = getById(id);
        if (module.isEnabled()) {
            throw new EntityNotFoundException("MODULE IS ALREADY ENABLE");
        }
        module.setSoftDelete(false);
        moduleRepository.save(module);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ModuleResponse getByIdResponse(String id) throws EntityNotFoundException {
        return moduleMapper.map(moduleRepository.findByModuleIdAndSoftDeleteFalse(id));
    }

    @Override
    public ModuleEntity getByIdAndSoftDeleteFalse(String id)  {
        Optional<ModuleEntity> optional = Optional.ofNullable(moduleRepository.findByModuleIdAndSoftDeleteFalse(id));
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Module not found or disabled");
        }
        return optional.get();
    }

    @Override
    @Transactional(readOnly = true)
    public ListModuleResponse getModules() throws EntityNotFoundException {
        List<ModuleEntity> modules = moduleRepository.findAll();
        return buildListModuleResponse(modules);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ModuleResponse addVideo(UpdateVideoIdRequest request) throws Exception {
        ModuleEntity moduleEntity = getByIdAndSoftDeleteFalse(request.getIdModulo());
        VideoEntity videoEntity = videoService.getByIdAndSoftDeleteFalse(request.getIdVideo());
        if (videoEntity.getConnected()) {
            throw new Exception("Video belong to a Module");
        }
        List<VideoEntity> list = videoService.getVideoByModuleId(moduleEntity.getModuleId());
        list.add(videoEntity);
        moduleEntity.setVideosCourse(list);
        videoService.updateConnected(videoEntity);
        return moduleMapper.map(moduleRepository.save(moduleEntity));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ModuleResponse removeVideoFromModule(DeleteVideoByModuleRequest request) {
        ModuleEntity moduleEntity = getByIdAndSoftDeleteFalse(request.getIdModulo());
        List<VideoEntity> list = videoService.getVideoByModuleId(moduleEntity.getModuleId());
        VideoEntity videoEntity = videoService.getByIdAndSoftDeleteFalse(request.getIdVideo());
        validateVideoBelongsList(videoEntity.getId(), list);
        for (VideoEntity aux : list) {
            if (aux.equals(videoEntity)) {
                videoService.removeModuleVideo(videoEntity, moduleEntity);
            }
        }
        return moduleMapper.map(moduleEntity);
    }

    @Override
    public List<ModuleEntity> getModuleListByCourseId(String idCourse) {
        return moduleRepository.getModulesByCourseId(idCourse);
    }

    @Override
    public void updateConnected(ModuleEntity entity) throws Exception {
        validateCourseId(entity);
        entity.setConnected(true);
        moduleRepository.save(entity);
    }

    @Override
    public void removeModuleCourse(ModuleEntity moduleEntity, CourseEntity courseEntity) {
        moduleRepository.deleteModuleByCourseId(moduleEntity.getModuleId(), courseEntity.getCourseId());
        moduleEntity.setConnected(false);
        moduleRepository.save(moduleEntity);
    }

    @Transactional(readOnly = true)
    private ModuleEntity getById(String id) {
        Optional<ModuleEntity> optional = moduleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException("Module not found.");
        }
        return optional.get();
    }

    private ListModuleResponse buildListModuleResponse(List<ModuleEntity> modules) {
        List<ModuleResponse> moduleResponses = moduleMapper.map(modules);
        ListModuleResponse responses = new ListModuleResponse();
        responses.setResponseList(moduleResponses);
        return responses;
    }

    @Transactional(readOnly = true)
    private void validateVideoBelongsList(String id, List<VideoEntity> list) {
        if (list == null) {
            throw new EntityNotFoundException("Video list null or disable.");
        }
        boolean exist = false;
        for (VideoEntity aux : list) {
            if (aux.equals(videoService.getByIdAndSoftDeleteFalse(id))) {
                exist = true;
            }
        }
        if (!exist) {
            throw new EntityNotFoundException("Video not found in this module.");
        }
    }

    private void validateCourseId(ModuleEntity entity) throws Exception {
        if (entity.getConnected()) {
            throw new Exception("This module belongs to a course");
        }
    }

    public String getModuleName(String idModule) {
        return getById(idModule).getName();
    }

    public String getCourseByModuleId(String moduleId) {
        return moduleRepository.getCourseByModuleId(moduleId);
    }
}


