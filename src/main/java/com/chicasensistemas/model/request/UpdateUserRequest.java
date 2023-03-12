package com.chicasensistemas.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "user_id cannot be empty or null.")
    private String userId;

    @NotBlank(message = "First name cannot be empty or null.")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty or null.")
    private String lastName;

    @Email(message = "Email has invalid format.")
    @NotBlank(message = "Email field can not be null or empty.")
    @Size(min = 5, max = 150, message = "Email need to have between 5 and 150 characters.")
    private String email;

    private String password;

    private String phone;

    private String photo;

    private String description;

}
