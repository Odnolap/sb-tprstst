package ru.odnolap.messages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility =  ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class PayMessageRequest {

    @JsonProperty("payment_id")
    private Integer paymentId;

    private Double sum;

    public PayMessageRequest() {
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public Double getSum() {
        return sum;
    }
}
