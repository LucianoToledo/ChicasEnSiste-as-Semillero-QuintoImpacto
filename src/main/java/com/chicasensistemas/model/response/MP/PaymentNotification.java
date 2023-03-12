package com.chicasensistemas.model.response.MP;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.Date;

@lombok.Data
@Getter
public class PaymentNotification {
    @JsonProperty("action")
    String action;

    @JsonProperty("api_version")
    String api_version;

    @JsonProperty("data")
    Data data; //payment id

    @JsonProperty("date_created")
    Date date_created;

    @JsonProperty("id")
    long id;

    @JsonProperty("live_mode")
    boolean live_mode;

    @JsonProperty("type")
    String type;

    @JsonProperty("user_id")
    String user_id;

}
