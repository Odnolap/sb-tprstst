package ru.odnolap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.odnolap.domain.Payment;
import ru.odnolap.domain.PaymentRepository;
import ru.odnolap.messages.PayMessageRequest;
import ru.odnolap.messages.PayMessageResponse;
import ru.odnolap.messages.PrepareMessageRequest;
import ru.odnolap.messages.PrepareMessageResponse;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping(value = "/rest")
public class PaymentRestController {

    @Autowired
    private PaymentRepository  repository;

    // ???
    @RequestMapping(value = "/prepare", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PrepareMessageResponse prepare(@RequestBody PrepareMessageRequest prepareMessageRequest) {
        if (prepareMessageRequest.getArticle() == null
                || prepareMessageRequest.getArticle().length() == 0
                || prepareMessageRequest.getArticle().length() > 100) {
            throw new RuntimeException("Invalid article.");
        } else if (prepareMessageRequest.getContragentId() == null
                || prepareMessageRequest.getContragentId() % 2 == 1) {
            throw new RuntimeException("Invalid contragent_id.");
        }
        return new PrepareMessageResponse(repository.save(prepareMessageRequest.toPayment()));
    }

    // ???
    @RequestMapping(value = "/pay", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PayMessageResponse pay(@RequestBody PayMessageRequest payMessageRequest) {
        Payment payment = repository.get(payMessageRequest.getPaymentId());
        if (payment == null
                || payment.getStatus() == 1
                || payment.getSum() == null
                || payMessageRequest.getSum() == null
                || Math.abs(payMessageRequest.getSum() - payment.getSum()) > 0.0001) {
            throw new RuntimeException("Confirm error.");
        }
        payment.setStatus(1);
        Date authorizationTime = new Date();
        payment.setAuthorizationTime(authorizationTime);
        repository.save(payment);
        return new PayMessageResponse(authorizationTime);
    }

    /*
    Проверочные вызовы (корректные):
    http://localhost:8080/rest/prepare?article=%D0%90%D1%80%D1%82%D0%B8%D0%BA%D1%83%D0%BB%201&contragent_id=34&contragent_time=2016-08-05T15:50:22
    http://localhost:8080/rest/prepare?article=309928-00&contragent_id=62&contragent_time=2016-08-05T21:48:11
    Проверочный вызов (некорректный - id контрагнте нечетное):
    http://localhost:8080/rest/prepare?article=309928-00&contragent_id=61&contragent_time=2016-08-05T18:48:11
     */
    @RequestMapping(value = "/prepare", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PrepareMessageResponse prepare2(@RequestParam("article") String article, @RequestParam("contragent_id") Integer contragentId,
                                           @RequestParam("contragent_time") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") Date contragentTime) {
        if (article == null
                || article.length() == 0
                || article.length() > 100) {
            throw new RuntimeException("Invalid article.");
        } else if (contragentId == null
                || contragentId % 2 == 1) {
            throw new RuntimeException("Invalid contragent_id.");
        }
        return new PrepareMessageResponse(repository.save(new Payment(article, contragentId, contragentTime)));
    }

    /*
    Проверочные вызовы (корректные при первом вызове, при втором будет ошибка, т.к. статус уже == 1):
    http://localhost:8080/rest/pay?payment_id=1&sum=584.12
    http://localhost:8080/rest/pay?payment_id=3&sum=122.03
    Проверочный вызов (некорректный - сумма некорректная):
    http://localhost:8080/rest/pay?payment_id=2&sum=222.22
    Проверочный вызов (некорректный - платеж уже подтвержден):
    http://localhost:8080/rest/pay?payment_id=4&sum=584.61
    Проверочный вызов (некорректный - несуществующий id платежа):
    http://localhost:8080/rest/pay?payment_id=8888&sum=584.61
     */
    @RequestMapping(value = "/pay", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PayMessageResponse pay2(@RequestParam("payment_id") Integer id, @RequestParam("sum") Double sum) {
        Payment payment = repository.get(id);
        if (payment == null
                || payment.getStatus() == 1
                || payment.getSum() == null
                || sum == null
                || Math.abs(sum - payment.getSum()) > 0.0001) {
            throw new RuntimeException("Confirm error.");
        }
        payment.setStatus(1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        Date authorizationTime = calendar.getTime();
        payment.setAuthorizationTime(authorizationTime);
        repository.save(payment);
        return new PayMessageResponse(authorizationTime);
    }
}
