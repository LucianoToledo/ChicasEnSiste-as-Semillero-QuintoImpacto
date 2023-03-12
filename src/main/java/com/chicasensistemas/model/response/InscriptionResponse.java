package com.chicasensistemas.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class InscriptionResponse {

    private String id;
    private String courseName;
    private String courseId;
    private String userEmail;
    private LocalDateTime inscritionDate;
    private Boolean softDelete;
}
