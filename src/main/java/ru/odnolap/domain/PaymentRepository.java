package ru.odnolap.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Repository
public class PaymentRepository  {

    @Autowired
    PaymentRepositoryProxy proxy;

    public List<Payment> findAll(){
        return proxy.findAll();
    }

    public Payment get(Integer id) {
        return proxy.getOne(id);
    }

    public Payment save(Payment payment) {
        if (payment.getSum() == null) {
            BigDecimal bd = new BigDecimal(ThreadLocalRandom.current().nextDouble(100d, 100000d));
            payment.setSum(bd.setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        return proxy.save(payment);
    }

    public List<Payment> filter
            (String productArticle, Integer contragentIdFrom, Integer contragentIdTo,
             Double sumFrom, Double sumTo, Integer statusFrom, Integer statusTo,
             Date contragentTimeFrom, Date contragentTimeTo,
             Date registratioinDateFrom, Date RegistrationDateTo,
             Date authorizationDateFrom, Date authorizationDateTo){
        return proxy.findByProductArticleLikeAndContragentIdBetweenAndSumBetweenAndStatusBetweenAndContragentTimeBetweenAndRegistrationTimeBetweenAndAuthorizationTimeBetweenOrderByRegistrationTimeDesc(
                productArticle, contragentIdFrom, contragentIdTo, sumFrom, sumTo, statusFrom, statusTo, contragentTimeFrom, contragentTimeTo,
                registratioinDateFrom, RegistrationDateTo, authorizationDateFrom, authorizationDateTo
        );
    }

    public List<Payment> filterWithoutRegistrationTime
            (String productArticle, Integer contragentIdFrom, Integer contragentIdTo,
             Double sumFrom, Double sumTo, Integer statusFrom, Integer statusTo,
             Date contragentTimeFrom, Date contragentTimeTo,
             Date registratioinDateFrom, Date RegistrationDateTo){
        return proxy.findByProductArticleLikeAndContragentIdBetweenAndSumBetweenAndStatusBetweenAndContragentTimeBetweenAndRegistrationTimeBetweenOrderByRegistrationTimeDesc(
                productArticle, contragentIdFrom, contragentIdTo, sumFrom, sumTo, statusFrom, statusTo, contragentTimeFrom, contragentTimeTo,
                registratioinDateFrom, RegistrationDateTo
        );
    }
}
