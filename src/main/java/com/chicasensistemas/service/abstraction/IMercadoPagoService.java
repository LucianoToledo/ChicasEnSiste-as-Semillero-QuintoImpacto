package com.chicasensistemas.service.abstraction;

import com.chicasensistemas.model.request.PaymentRequest;
import com.chicasensistemas.model.response.MP.PaymentUrlResponse;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;

import java.io.IOException;

public interface IMercadoPagoService {
    PaymentUrlResponse createPreference(PaymentRequest paymentRequest) throws MPException, MPApiException;

//    PreferencePayerRequest payer(UserEntity userEntity);
//
//    List<PreferenceItemRequest> items(CourseEntity courseEntity);
//
//    List<PreferencePaymentTypeRequest> createExcludedPaymentTypes();
//
//    ObjectNode connectionMpPayment(String idPayment) throws IOException;
//
//    PaymentResponse createPaymentResponse(ObjectNode objectNode) throws JsonProcessingException;

    void notificationMP(String notificationJson) throws IOException;

}
