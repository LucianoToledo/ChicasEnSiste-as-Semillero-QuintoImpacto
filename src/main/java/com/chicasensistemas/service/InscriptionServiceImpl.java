package com.chicasensistemas.service;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.mapper.CourseMapper;
import com.chicasensistemas.mapper.InscriptionMapper;
import com.chicasensistemas.model.entity.CourseEntity;
import com.chicasensistemas.model.entity.InscriptionEntity;
import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.model.request.InscriptionRequest;
import com.chicasensistemas.model.response.InscriptionResponse;
import com.chicasensistemas.model.response.ListInscriptionsReponse;
import com.chicasensistemas.repository.ICourseRepository;
import com.chicasensistemas.repository.IUserRepository;
import com.chicasensistemas.repository.InscriptionRepository;
import com.chicasensistemas.service.abstraction.IInscrptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InscriptionServiceImpl implements IInscrptionService {

    @Autowired
    private InscriptionRepository inscriptionRepository;

    @Autowired
    private InscriptionMapper inscriptionMapper;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private CourseMapper courseMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public InscriptionResponse createInscription(InscriptionRequest inscriptionRequest) {
        UserEntity user = userRepository.findByUserIdAndSoftDeleteFalse(inscriptionRequest.getUserId());
        CourseEntity course = courseRepository.findByCourseIdAndSoftDeleteFalse(inscriptionRequest.getCourseId());
        validateInscription(user, course);
        InscriptionEntity inscription = new InscriptionEntity();
        inscription.setUser(user);
        inscription.setCourse(course);
        return inscriptionMapper.map(inscriptionRepository.save(inscription));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public InscriptionResponse enableInscription(String id) throws EntityNotFoundException {
        InscriptionEntity inscription = findById(id);
        if (inscription.isEnabled()) {
            throw new EntityNotFoundException("Inscription is already enable");
        }
        inscription.setSoftDelete(false);
        return inscriptionMapper.map(inscriptionRepository.save(inscription));
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public InscriptionResponse disableInscription(String id) throws EntityNotFoundException {
        InscriptionEntity inscription = findById(id);
        if (!inscription.isEnabled()) {
            throw new EntityNotFoundException("Inscription is already disable");
        }
        inscription.setSoftDelete(true);
        return inscriptionMapper.map(inscriptionRepository.save(inscription));
    }

    @Transactional(readOnly = true)
    private InscriptionEntity findById(String idInscription) {
        Optional<InscriptionEntity> opt = inscriptionRepository.findById(idInscription);
        if (opt.isEmpty()) {
            throw new EntityNotFoundException("Inscription not found");
        }
        return opt.get();
    }

    @Override
    @Transactional(readOnly = true)
    public InscriptionResponse findInscriptionById(String id) throws EntityNotFoundException {
        return inscriptionMapper.map(findById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ListInscriptionsReponse getInscriptions() throws EntityNotFoundException {
        List<InscriptionEntity> inscriptions = inscriptionRepository.findAll();
        return buildListResponse(inscriptions);
    }

    @Override
    @Transactional(readOnly = true)
    public ListInscriptionsReponse getInscriptionByUserId(String userId) {
        List<InscriptionEntity> inscriptions = inscriptionRepository.findInscriptionByUserId(userRepository.findByUserIdAndSoftDeleteFalse(userId));
        return buildListResponse(inscriptions);
    }

    @Override
    @Transactional(readOnly = true)
    public ListInscriptionsReponse getInscriptionByCourseId(String courseId) {
        List<InscriptionEntity> inscriptions = inscriptionRepository.findInscriptionByNameCourse(courseRepository.findByCourseIdAndSoftDeleteFalse(courseId));
        return buildListResponse(inscriptions);
    }

    private ListInscriptionsReponse buildListResponse(List<InscriptionEntity> inscriptions) {
        List<InscriptionResponse> inscriptionResponses = inscriptionMapper.map(inscriptions);
        ListInscriptionsReponse listInscriptionsReponse = new ListInscriptionsReponse();
        listInscriptionsReponse.setInscriptionResponseList(inscriptionResponses);
        return listInscriptionsReponse;
    }

    @Transactional(readOnly = true)
    public void validateInscription(UserEntity user, CourseEntity course) throws EntityNotFoundException {
        InscriptionEntity inscription = inscriptionRepository.findInscriptionEntityByUserEntityAndCourseEntity(user, course);
        if (user == null) {
            throw new EntityNotFoundException("Inscription rejected. User not found or not enabled");
        }
        if (course == null) {
            throw new EntityNotFoundException("Inscription rejected. Course not found or not enabled");
        }
        if (inscription != null) {
            throw new EntityNotFoundException("User is already inscripted in the course");
        }
    }

}
