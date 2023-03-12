package com.chicasensistemas.service.abstraction;

import com.chicasensistemas.exception.EntityNotFoundException;
import com.chicasensistemas.model.entity.UserEntity;
import com.chicasensistemas.model.request.UpdateUserPasswordRequest;
import com.chicasensistemas.model.request.UserPasswordRecoveryRequest;
import com.chicasensistemas.model.response.UserPasswordRecoveryResponse;

import javax.servlet.http.HttpServletRequest;

public interface IPasswordRecoveryService {

    UserPasswordRecoveryResponse forgottenPassword (UserPasswordRecoveryRequest request, HttpServletRequest httpServletRequest) throws EntityNotFoundException;

    UserPasswordRecoveryResponse resetPassword (UpdateUserPasswordRequest passwordRequest) throws Exception;

    public UserEntity validatePasswordResetRequest(String resetToken);


}
