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
public class IdInscriptionRequest {

    @NotBlank(message ="inscription_id cannot be empty or null.")
    private String idInscription;
}
