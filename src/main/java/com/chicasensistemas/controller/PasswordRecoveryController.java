package com.chicasensistemas.controller;


import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.request.UserPasswordRecoveryRequest;
import com.chicasensistemas.model.request.UpdateUserPasswordRequest;
import com.chicasensistemas.model.response.UserPasswordRecoveryResponse;
import com.chicasensistemas.service.abstraction.IPasswordRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/password")
public class PasswordRecoveryController {

    @Autowired
    private IPasswordRecoveryService iPasswordRecoveryService;

    @PostMapping("/forgot")
    public ResponseEntity <UserPasswordRecoveryResponse> forgottenPassword (@RequestBody @Valid UserPasswordRecoveryRequest request, HttpServletRequest httpServletRequest )
            throws EntityNotFoundException {
        return ResponseEntity.ok(iPasswordRecoveryService.forgottenPassword(request,httpServletRequest));
    }

    @PutMapping("/reset")
    public ResponseEntity <UserPasswordRecoveryResponse> resetPassword (@RequestBody @Valid UpdateUserPasswordRequest passwordRequest) throws Exception {
        return ResponseEntity.ok(iPasswordRecoveryService.resetPassword(passwordRequest));
    }

}
