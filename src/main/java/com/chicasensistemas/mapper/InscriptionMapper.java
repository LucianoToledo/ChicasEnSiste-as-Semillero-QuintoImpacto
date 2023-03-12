package com.chicasensistemas.mapper;

import com.chicasensistemas.model.entity.InscriptionEntity;
import com.chicasensistemas.model.response.InscriptionResponse;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class InscriptionMapper {

    public InscriptionResponse map(InscriptionEntity inscription) {
        InscriptionResponse inscriptionResponse = new InscriptionResponse();
        inscriptionResponse.setId(inscription.getInscriptionId());
        inscriptionResponse.setCourseName(inscription.getCourse().getNameCourse());
        inscriptionResponse.setCourseId(inscription.getCourse().getCourseId());
        inscriptionResponse.setUserEmail(inscription.getUser().getEmail());
        inscriptionResponse.setSoftDelete(inscription.getSoftDelete());
        inscriptionResponse.setInscritionDate(inscription.getInscritionDate());
        return inscriptionResponse;
    }

    public List<InscriptionResponse> map(List<InscriptionEntity> inscriptions) {
        List<InscriptionResponse> inscriptionResponses = new ArrayList<>(inscriptions.size());
        for (InscriptionEntity inscription : inscriptions) {
            inscriptionResponses.add(map(inscription));
        }
        return inscriptionResponses;
    }
}
