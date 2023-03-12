package com.chicasensistemas.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InscriptionRequest {

    @NotBlank(message = "course name cannot be empty or null")
    private String courseId;

    @NotBlank(message = "user email cannot be empty or null")
    private String userId;
}
