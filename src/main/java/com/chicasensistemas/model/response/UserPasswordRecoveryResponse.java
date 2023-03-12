package com.chicasensistemas.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPasswordRecoveryResponse {

    private  String userId;
    private  String email;
     private String resetToken;

}
