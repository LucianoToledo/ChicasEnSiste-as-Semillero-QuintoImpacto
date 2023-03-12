package com.chicasensistemas.model.response;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CourseResponse {

    private String id;
    private String nameCourse;
    private String description;
    private String price;
    private String duration;
    private Boolean softDelete;
    private List<ModuleResponse> modules;
}
