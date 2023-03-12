package com.chicasensistemas.model.response;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ModuleResponse {

    private String id;
    private String name;
    private String description;
    private Boolean softDelete;
    private Boolean connected;
    private String idCourse;
    private String courseName;
    private List<VideoResponse> videosCourse;
}
