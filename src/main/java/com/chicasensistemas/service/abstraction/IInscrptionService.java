package com.chicasensistemas.service.abstraction;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.entity.CourseEntity;
import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.model.request.InscriptionRequest;
import com.chicasensistemas.model.response.InscriptionResponse;
import com.chicasensistemas.model.response.ListInscriptionsReponse;

public interface IInscrptionService {
    InscriptionResponse createInscription(InscriptionRequest inscriptionRequest);

    InscriptionResponse disableInscription(String id) throws EntityNotFoundException;

    InscriptionResponse enableInscription(String id) throws EntityNotFoundException;

    ListInscriptionsReponse getInscriptionByUserId(String userId) throws EntityNotFoundException;

    ListInscriptionsReponse getInscriptions() throws EntityNotFoundException;

    ListInscriptionsReponse getInscriptionByCourseId(String courseId) throws EntityNotFoundException;

    InscriptionResponse findInscriptionById(String id) throws EntityNotFoundException;

    void validateInscription(UserEntity user, CourseEntity course) throws EntityNotFoundException;

}
