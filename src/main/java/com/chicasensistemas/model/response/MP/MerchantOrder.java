package com.chicasensistemas.model.response.MP;

import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
public class MerchantOrder {
    @JsonProperty("resource")
    private String resource;

    @JsonProperty("topic")
    private String topic;

}
