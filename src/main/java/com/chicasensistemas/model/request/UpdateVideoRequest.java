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

public class UpdateVideoRequest {

    @NotBlank(message = "video_id cannot be empty or null.")
    private String id;

    @NotBlank(message = "url cannot be empty or null.")
    private String url;

    @NotBlank(message = "name cannot be empty or null.")
    private String name;

    @NotBlank(message = "description cannot be empty or null.")
    private String duration;

}
