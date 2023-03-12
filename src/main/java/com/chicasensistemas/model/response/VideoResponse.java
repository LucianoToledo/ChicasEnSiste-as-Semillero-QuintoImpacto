package com.chicasensistemas.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoResponse {

    private String id;
    private String url;
    private String name;
    private String duration;
    private Boolean connected;
    private Boolean softDelete;
    private String idModulo;
    private String moduleName;

}
