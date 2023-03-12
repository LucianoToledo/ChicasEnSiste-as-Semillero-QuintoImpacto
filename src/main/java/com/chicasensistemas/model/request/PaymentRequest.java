package com.chicasensistemas.model.request;

import lombok.Data;

import java.util.List;

@Data
public class PaymentRequest {
    private String idUser;
    private String idCourse;
}
