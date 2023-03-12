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
public class CourseRequest {

    @NotBlank(message ="name cannot be empty or null.")
    private String nameCourse;

    @NotBlank(message = "description cannot be empty or null.")
    private String description;

    @NotBlank(message = "price cannot be empty or null.")
    private String price;

    @NotBlank(message = "duration cannot be empty or null.")
    private String duration;

    private String userAdminId;

}
