package ru.odnolap.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Integer id; // id платежа

    @NotEmpty
    @Column(name = "product_article")
    private String productArticle; // артикул товара

    @NotNull
    @Column(name = "contragent_id")
    private Integer contragentId; // id контрагента

    @NotNull
    @Column(name = "contragent_time")
    private Date contragentTime; // дата платежа контрагента

    @Column(name = "summ")
    @Digits(fraction = 2, integer = 10)
    private Double sum; // сумма платежа

    @Column(name = "registration_time")
    private Date registrationTime; // дата регистрация платежа в системе

    @Column(name = "authorization_time")
    private Date authorizationTime; // дата подтверждения платежа в системе

    private Integer status = 0; // статус платежа: 0 - зарегистрирован, 1 - подтвержден

    public Payment() {}

    public Payment(String productArticle, Integer contragentId, Date contragentTime) {
        this.productArticle = productArticle;
        this.contragentId = contragentId;
        this.contragentTime = contragentTime;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        registrationTime = calendar.getTime();
        status = 0;
    }

    public Payment(String productArticle, Integer contragentId, Date contragentTime, Double sum) {
        this.productArticle = productArticle;
        this.contragentId = contragentId;
        this.contragentTime = contragentTime;
        this.sum = sum;
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        registrationTime = calendar.getTime();
        status = 0;
    }

    public Integer getId() {
        return id;
    }

    public String getProductArticle() {
        return productArticle;
    }

    public void setProductArticle(String productArticle) {
        this.productArticle = productArticle;
    }

    public Integer getContragentId() {
        return contragentId;
    }

    public void setContragentId(Integer contragentId) {
        this.contragentId = contragentId;
    }

    public Date getContragentTime() {
        return contragentTime;
    }

    public void setContragentTime(Date contragentTime) {
        this.contragentTime = contragentTime;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = registrationTime;
    }

    public Date getAuthorizationTime() {
        return authorizationTime;
    }

    public void setAuthorizationTime(Date authorizationTime) {
        this.authorizationTime = authorizationTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
