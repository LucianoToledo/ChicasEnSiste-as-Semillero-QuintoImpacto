package com.chicasensistemas.controller;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.request.InscriptionRequest;
import com.chicasensistemas.model.response.InscriptionResponse;
import com.chicasensistemas.model.response.ListInscriptionsReponse;
import com.chicasensistemas.service.abstraction.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/inscription")
public class InscriptionController {

    @Autowired
    private IInscrptionService iInscrptionService;

    @PostMapping("/create")
    public ResponseEntity<InscriptionResponse> create(@RequestBody @Valid InscriptionRequest request) {
        InscriptionResponse response = iInscrptionService.createInscription(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<InscriptionResponse> disableInscription(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(iInscrptionService.disableInscription(id));

        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/enable/{id}")
    public ResponseEntity<InscriptionResponse> enableInscription(@PathVariable String id) {
        try {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(iInscrptionService.enableInscription(id));
        } catch (EntityNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping
    public ResponseEntity<ListInscriptionsReponse> getInscriptions() throws EntityNotFoundException {
        return ResponseEntity.ok().body(iInscrptionService.getInscriptions());
    }

    @GetMapping("/user/{id}") //   /inscription/user/:id
    public ResponseEntity<ListInscriptionsReponse> getInscriptionByUser(@PathVariable String id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(iInscrptionService.getInscriptionByUserId(id));
    }

    @GetMapping("/course/{id}")
    public ResponseEntity<ListInscriptionsReponse> getInscriptionByCourse(@PathVariable String id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(iInscrptionService.getInscriptionByCourseId(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscriptionResponse> getInscription(@PathVariable String id) throws EntityNotFoundException {
        return ResponseEntity.ok().body(iInscrptionService.findInscriptionById(id));
    }

}
