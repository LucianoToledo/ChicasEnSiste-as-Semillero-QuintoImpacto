package com.chicasensistemas.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordRecoveryRequest {

    @NotBlank(message = "Email cannot be null or empty.")
    String email;
}
