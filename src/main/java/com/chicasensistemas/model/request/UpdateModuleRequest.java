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
public class UpdateModuleRequest {

    @NotBlank(message = "module_id cannot be empty or null.")
    private String id;

    @NotBlank(message = "name cannot be empty or null.")
    private String name;

    @NotBlank(message = "description cannot be empty or null.")
    private String description;

}
