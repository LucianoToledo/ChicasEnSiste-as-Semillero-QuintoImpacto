package com.chicasensistemas;

import com.mercadopago.MercadoPagoConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ChicasEnSistemas {

    @GetMapping("/")
    String home() {
        return "Spring is here!";
    }

    public static void main(String[] args) {
        SpringApplication.run(ChicasEnSistemas.class, args);

    }
}