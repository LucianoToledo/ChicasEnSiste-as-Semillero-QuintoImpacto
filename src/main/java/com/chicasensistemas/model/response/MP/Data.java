package com.chicasensistemas.model.response.MP;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class Data {
    @JsonProperty("id")
    String id; //payment id
}

