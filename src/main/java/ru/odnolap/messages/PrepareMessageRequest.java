package ru.odnolap.messages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import ru.odnolap.domain.Payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

@JsonAutoDetect(fieldVisibility =  ANY, getterVisibility = NONE, isGetterVisibility = NONE, setterVisibility = NONE)
public class PrepareMessageRequest {

    private String article;

    @JsonProperty("contragent_id")
    private Integer contragentId;

    @JsonProperty("contragent_time")
    private String contragentTime;

    public PrepareMessageRequest(){
    }

    public Payment toPayment() {
        try {
            return new Payment(article, contragentId, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(contragentTime));
        } catch (ParseException e) {
            return new Payment(article, contragentId, new Date());
        }
    }

    public String getArticle() {
        return article;
    }

    public Integer getContragentId() {
        return contragentId;
    }
}
