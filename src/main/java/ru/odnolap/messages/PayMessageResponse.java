package ru.odnolap.messages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility =  ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class PayMessageResponse {
    @JsonProperty("time")
    private String authorizationTime;

    public PayMessageResponse(){
    }

    public PayMessageResponse(Date authorizationTime) {
        this.authorizationTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(authorizationTime);
    }

}

