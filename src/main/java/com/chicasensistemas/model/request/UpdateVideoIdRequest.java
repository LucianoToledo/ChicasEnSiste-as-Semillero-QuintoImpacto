package com.chicasensistemas.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateVideoIdRequest {

    @NotBlank(message = "video_id cannot be empty or null")
    private String idVideo;

    @NotBlank(message = "module_id cannot be empty or  null")
    private String idModulo;
}
