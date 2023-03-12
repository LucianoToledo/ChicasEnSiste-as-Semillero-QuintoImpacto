package com.chicasensistemas.service.abstraction;

import com.chicasensistemas.model.response.AuthenticationResponse;

public interface IGoogle {

  Boolean emailExist(String email);
  AuthenticationResponse loginGoogle(String email);
  AuthenticationResponse registerGoogle(String email, String firsName, String lasName);

}
