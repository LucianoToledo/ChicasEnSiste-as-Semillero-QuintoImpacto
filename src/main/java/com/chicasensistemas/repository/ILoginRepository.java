package com.chicasensistemas.repository;

import com.chicasensistemas.exception.InvalidCredentialsException;
import com.chicasensistemas.model.request.AuthenticationRequest;
import com.chicasensistemas.model.response.AuthenticationResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoginRepository {

  AuthenticationResponse login(AuthenticationRequest request) throws InvalidCredentialsException;
}
