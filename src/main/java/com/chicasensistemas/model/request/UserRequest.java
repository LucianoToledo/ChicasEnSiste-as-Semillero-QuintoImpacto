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
public class UserRequest {

  @NotBlank(message = "First name cannot be empty or null.")
  private String firstName;

  @NotBlank(message = "Last name cannot be empty or null.")
  private String lastName;

  @NotBlank(message = "Email cannot be empty or null.")
  @Email(message = "Email is not valid.")
  private String email;

  @NotBlank(message = "Password cannot be empty or null.")
  @Size(min = 8,message="The password must be at least eight characters.")
  private String password;

}
