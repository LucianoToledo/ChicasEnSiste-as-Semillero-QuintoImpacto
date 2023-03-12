package com.chicasensistemas.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

  private String userId;

  private String firstName;

  private String lastName;

  private String email;

  private String phone;

  private String photo;

  private String description;

  private String role;

  private String token;

  private String resetToken;

}
