package com.chicasensistemas.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ListCourseResponse {

    @JsonProperty("courses")
    private List<CourseResponse> courseResponseList;
}