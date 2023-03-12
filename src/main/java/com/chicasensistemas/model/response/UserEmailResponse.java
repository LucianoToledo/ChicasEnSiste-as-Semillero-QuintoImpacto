package com.chicasensistemas.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserEmailResponse {
    private String firstName;

    private String lastName;

    private String email;
}
