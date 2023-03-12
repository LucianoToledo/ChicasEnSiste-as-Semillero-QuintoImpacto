package com.chicasensistemas.service;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.mapper.CourseMapper;
import com.chicasensistemas.model.entity.CourseEntity;
import com.chicasensistemas.model.entity.ModuleEntity;
import com.chicasensistemas.model.request.ModuleToCourseRequest;
import com.chicasensistemas.model.request.CourseRequest;
import com.chicasensistemas.model.request.UpdateCourseRequest;
import com.chicasensistemas.model.response.CourseResponse;
import com.chicasensistemas.model.response.ListCourseResponse;
import com.chicasensistemas.repository.ICourseRepository;
import com.chicasensistemas.service.abstraction.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Lazy
    @Autowired
    private ModuleServiceImpl moduleService;

    @Autowired
    private UserServiceImpl userService;


    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CourseResponse createCourse(CourseRequest request) throws Exception {
        validateCourse(request.getNameCourse());
        CourseEntity course = courseMapper.map(request);
        course.setUserEntity(userService.getByIdAndEnabled(request.getUserAdminId()));
        return courseMapper.map(courseRepository.save(course));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CourseResponse enableCourse(String id) throws EntityNotFoundException {
        CourseEntity course = courseRepository.findByCourseId(id);
        if (course.isEnabled()) {
            throw new EntityNotFoundException("Course is already enable");
        }
        course.setSoftDelete(false);
        return courseMapper.map(courseRepository.save(course));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CourseResponse disableCourse(String id) throws EntityNotFoundException {
        CourseEntity course = courseRepository.findByCourseId(id);
        if (!course.isEnabled()) {
            throw new EntityNotFoundException("Course is already disable");
        }
        course.setSoftDelete(true);
        return courseMapper.map(courseRepository.save(course));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CourseResponse updateCourse(UpdateCourseRequest courseRequest) {
        CourseEntity course = getByIdAndSoftDeleteFalse(courseRequest.getId());
        course.setNameCourse(courseRequest.getName());
        course.setDescription(courseRequest.getDescription());
        course.setPrice(courseRequest.getPrice());
        course.setDuration(courseRequest.getDuration());
        return courseMapper.map(courseRepository.save(course));
    }

    @Transactional(rollbackFor = {Exception.class})
    public CourseEntity findByNameAndSoftDeleteFalse(String nameCourse) {
        Optional<CourseEntity> opt = Optional.ofNullable(courseRepository.findByNameCourse(nameCourse));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Course not found");
        }
        return opt.get();
    }

    @Override
    @Transactional(readOnly = true)
    public ListCourseResponse getCourses() throws EntityNotFoundException {
        List<CourseEntity> courses = courseRepository.findAll();
        return buildListResponse(courses);
    }

    private ListCourseResponse buildListResponse(List<CourseEntity> courses) {
        List<CourseResponse> courseResponses = courseMapper.map(courses);
        ListCourseResponse listCourseResponse = new ListCourseResponse();
        listCourseResponse.setCourseResponseList(courseResponses);
        return listCourseResponse;
    }

    @Override
    @Transactional
    public CourseResponse getByNameAndSoftDeleteFalse(String name) {
        return courseMapper.map(findByNameAndSoftDeleteFalse(name));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CourseResponse getByIdResponse(String id) throws EntityNotFoundException {
        return courseMapper.map(getByIdAndSoftDeleteFalse(id));
    }

    @Transactional(readOnly = true)
    public CourseEntity getById(String id) throws EntityNotFoundException {
        Optional<CourseEntity> opt = Optional.ofNullable(courseRepository.findByCourseIdAndSoftDeleteFalse(id));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Course not found");
        }
        return opt.get();
    }

    @Transactional(rollbackFor = {Exception.class})
    public CourseEntity getByIdAndSoftDeleteFalse(String id) throws EntityNotFoundException {
        Optional<CourseEntity> opt = Optional.ofNullable(courseRepository.findByCourseIdAndSoftDeleteFalse(id));
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Course not found");
        }
        return opt.get();
    }

    @Transactional(readOnly = true)
    private void validateCourse(String name) {
        CourseEntity course = courseRepository.findByNameCourse(name);
        if (course != null) {
            throw new EntityNotFoundException("Name is already in use");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public CourseResponse addModule(ModuleToCourseRequest request) throws Exception {
        CourseEntity courseEntity = getByIdAndSoftDeleteFalse(request.getIdCourse());
        ModuleEntity moduleEntity = moduleService.getByIdAndSoftDeleteFalse(request.getIdModule());
        if (moduleEntity.getConnected()) {
            throw new Exception("Module belong to a course");
        }
        List<ModuleEntity> list = moduleService.getModuleListByCourseId(courseEntity.getCourseId());
        list.add(moduleEntity);
        courseEntity.setModules(list);
        moduleService.updateConnected(moduleEntity);
        return courseMapper.map(courseRepository.save(courseEntity));
    }

    @Transactional
    public CourseResponse removeModule(ModuleToCourseRequest request) {
        CourseEntity courseEntity = getByIdAndSoftDeleteFalse(request.getIdCourse());
        ModuleEntity moduleEntity = moduleService.getByIdAndSoftDeleteFalse(request.getIdModule());
        List<ModuleEntity> list = moduleService.getModuleListByCourseId(courseEntity.getCourseId());
        validateModuleBelongsList(moduleEntity, list);
        for (ModuleEntity aux : list) {
            if (aux.equals(moduleEntity)) {
                moduleService.removeModuleCourse(moduleEntity, courseEntity);
            }
        }
        return courseMapper.map(courseEntity);
    }

    @Transactional(readOnly = true)
    private void validateModuleBelongsList(ModuleEntity module, List<ModuleEntity> list) {
        if (list == null) {
            throw new EntityNotFoundException("Module list null or disable.");
        }
        boolean exist = false;
        for (ModuleEntity aux : list) {
            if (aux.equals(module)) {
                exist = true;
            }
        }
        if (!exist) {
            throw new EntityNotFoundException("Module not found in this Course.");
        }
    }

    public String getCourseName(String idCourse) {
        return getById(idCourse).getNameCourse();
    }
}

