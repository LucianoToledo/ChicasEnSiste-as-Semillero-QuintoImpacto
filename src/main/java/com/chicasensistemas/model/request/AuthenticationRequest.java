package com.chicasensistemas.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

  @Email(message = "Email has invalid format.")
  @NotBlank(message = "Emails cannot be null or empty.")
  @Size(min = 5, max = 150, message = "Email need to have between 5 and 150 characters.")
  private String email;

  @NotBlank(message = "Password cannot be null or empty.")
  private String password;

}
