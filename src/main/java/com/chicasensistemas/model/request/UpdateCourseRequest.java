package com.chicasensistemas.model.request;

import com.chicasensistemas.model.entity.ModuleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseRequest {
    private String id;
    private String name;
    private String description;
    private String price;
    private String duration;
    private List<ModuleEntity> modules;
}
