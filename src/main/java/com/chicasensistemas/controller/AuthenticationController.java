package com.chicasensistemas.controller;

import com.chicasensistemas.exception.InvalidCredentialsException;
import com.chicasensistemas.exception.UserAlreadyExistException;
import com.chicasensistemas.model.request.AuthenticationRequest;
import com.chicasensistemas.model.request.TokenRequest;
import com.chicasensistemas.model.request.UserRequest;
import com.chicasensistemas.model.response.AuthenticationResponse;
import com.chicasensistemas.model.response.UserResponse;
import com.chicasensistemas.repository.ILoginRepository;
import com.chicasensistemas.service.abstraction.IGoogle;
import com.chicasensistemas.service.abstraction.IRegisterUser;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import java.io.IOException;
import java.util.Collections;
import javax.mail.MessagingException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  @Autowired
  private IRegisterUser registerUser;

  @Autowired
  private ILoginRepository login;

  @Autowired
  private IGoogle google;

  @Value("${google.clientId}")
  String googleClientId;

  @PostMapping("/register")
  public ResponseEntity<UserResponse> register(@RequestBody @Valid UserRequest request) throws UserAlreadyExistException, MessagingException {
    UserResponse response = registerUser.register(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthenticationResponse> login(
      @RequestBody @Valid AuthenticationRequest request)
      throws InvalidCredentialsException{
    return ResponseEntity.ok(login.login(request));
  }

//  @PostMapping("/google")
//  public ResponseEntity<AuthenticationResponse> google(@RequestBody TokenRequest tokenRequest) throws IOException {
//    final NetHttpTransport transport = new NetHttpTransport();
//    final JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();
//    GoogleIdTokenVerifier.Builder verifier =
//        new GoogleIdTokenVerifier.Builder(transport, jacksonFactory)
//            .setAudience(Collections.singletonList(googleClientId));
//    final GoogleIdToken googleIdToken = GoogleIdToken.parse(verifier.getJsonFactory(),tokenRequest.getValue());
//    final GoogleIdToken.Payload payload = googleIdToken.getPayload();
//    if (google.emailExist(payload.getEmail())){
//      return ResponseEntity.ok(google.loginGoogle(payload.getEmail()));
//    }else {
//      return ResponseEntity.ok(google.registerGoogle(payload.getEmail(), payload.get("given_name").toString(), payload.get("family_name").toString()));
//    }
//  }
  @PostMapping("/google")
  public ResponseEntity<AuthenticationResponse> google(@RequestBody TokenRequest tokenRequest) throws IOException {
    GoogleTokenResponse tokenResponse =
            new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    "https://oauth2.googleapis.com/token",
                    "36629231244-e0upt5956ekp6nld7tktsrbab7u8bruc.apps.googleusercontent.com",
                    "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",
                    tokenRequest.getValue(),
                    "postmessage")
                    .execute();
    
    GoogleIdToken token = tokenResponse.parseIdToken();

    // Get User's email
    String userEmail = token.getPayload().getEmail();

    if (google.emailExist(userEmail)){
      return ResponseEntity.ok(google.loginGoogle(userEmail));
    }else {
      return ResponseEntity.ok(google.registerGoogle(userEmail, token.getPayload().get("given_name").toString(), token.getPayload().get("family_name").toString()));
    }
  }
}
