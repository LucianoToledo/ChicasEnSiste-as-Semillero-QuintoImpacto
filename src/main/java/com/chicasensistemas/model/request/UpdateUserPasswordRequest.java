package com.chicasensistemas.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UpdateUserPasswordRequest {

    @NotBlank(message = "Password field can not be null or empty.")
    @Size(min = 8, message = "The password must be at least eight characters.")
    private String password;

    @NotBlank
    private String resetToken;

}
