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
public class VideoRequest {


    @NotBlank(message =  "URL cannot be null or empty")
    private String url;

    @NotBlank(message = "Name cannot be null or empty")
    private String name;

    @NotBlank(message = "Duration cannot be null or empty")
    private String duration;

    private String idModulo;
}
