package ru.odnolap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.odnolap.domain.Payment;
import ru.odnolap.service.PaymentService;
import ru.odnolap.to.PayMessageResponse;
import ru.odnolap.to.PrepareMessageResponse;

import java.util.Date;

@RestController
@RequestMapping(value = "/rest")
public class PaymentRestController {

    @Autowired
    private PaymentService service;

    /*
    Проверочные вызовы (корректные):
    http://localhost:8080/rest/prepare?article=%D0%90%D1%80%D1%82%D0%B8%D0%BA%D1%83%D0%BB%201&contragent_id=34&contragent_time=2016-08-05T15:50:22
    http://localhost:8080/rest/prepare?article=309928-00&contragent_id=62&contragent_time=2016-08-05T21:48:11
    Проверочный вызов (некорректный - id контрагнте нечетное):
    http://localhost:8080/rest/prepare?article=309928-00&contragent_id=61&contragent_time=2016-08-05T18:48:11
     */
    @RequestMapping(value = "/prepare", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PrepareMessageResponse prepare(@RequestParam("article") String article, @RequestParam("contragent_id") Integer contragentId,
                                          @RequestParam("contragent_time") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm:ss") Date contragentTime) {
        return new PrepareMessageResponse(service.save(new Payment(article, contragentId, contragentTime)));
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
    public PayMessageResponse pay(@RequestParam("payment_id") Integer id, @RequestParam("sum") Double sum) {
        return new PayMessageResponse(service.confirm(service.get(id), sum));
    }
}
