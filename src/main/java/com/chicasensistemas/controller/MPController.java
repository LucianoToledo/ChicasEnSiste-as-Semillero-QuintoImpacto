package com.chicasensistemas.controller;

import com.chicasensistemas.model.request.PaymentRequest;
import com.chicasensistemas.model.response.MP.PaymentUrlResponse;
import com.chicasensistemas.service.MercadoPagoService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/mp")
public class MPController {

    @Autowired
    MercadoPagoService mercadoPagoService;

    @PostMapping("/createAndRedirect")
    public ResponseEntity<PaymentUrlResponse> mp(@RequestBody PaymentRequest paymentRequest) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(mercadoPagoService.createPreference(paymentRequest));
        } catch (MPException e) {
            throw new RuntimeException(e);
        } catch (MPApiException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/notification")
    @ResponseStatus(HttpStatus.OK)
    @Async
    public void notification(@RequestBody String json) {
        try {
            mercadoPagoService.notificationMP(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

