package com.chicasensistemas.service.abstraction;

import com.chicasensistemas.exception.UserAlreadyExistException;
import com.chicasensistemas.model.request.UserRequest;
import com.chicasensistemas.model.response.UserResponse;

import javax.mail.MessagingException;

public interface IRegisterUser {

  UserResponse register(UserRequest request) throws UserAlreadyExistException, MessagingException;
}
