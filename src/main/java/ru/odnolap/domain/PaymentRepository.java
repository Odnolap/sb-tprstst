package ru.odnolap.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Repository
public class PaymentRepository  {
    private final static Sort SORT_REGISTRATION_TIME_DESC = new Sort(Sort.Direction.DESC, "registrationTime");
    private static final Date MIN_DATE_TIME;
    private static final Date MAX_DATE_TIME;
    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1900, Calendar.JANUARY, 1, 0, 0);
        MIN_DATE_TIME = calendar.getTime();
        calendar.set(2900, Calendar.JANUARY, 1, 0, 0);
        MAX_DATE_TIME = calendar.getTime();
    }

    @Autowired
    private PaymentRepositoryProxy proxy;

    public List<Payment> getAll(){
        return proxy.findAll(SORT_REGISTRATION_TIME_DESC);
    }

    public Payment get(Integer id) {
        return proxy.getOne(id);
    }

    public Payment save(Payment payment) {
        return proxy.save(payment);
    }

    public List<Payment> getFiltered
            (String productArticle, Integer contragentId,
             Double sumFrom, Double sumTo, Integer status,
             Date contragentDateFrom, Date contragentDateTo,
             Date registratioinDateFrom, Date registrationDateTo,
             Date authorizationDateFrom, Date authorizationDateTo){
        String productArticlePattern = productArticle == null ? "%" : productArticle.isEmpty() ? "%" : productArticle;
        Integer contragentIdFrom = contragentId == null ? -2000000000 : contragentId;
        Integer contragentIdTo = contragentId == null ? 2000000000 : contragentId;
        Integer statusFrom = status == null ? 0 : status;
        Integer statusTo = status == null ? 1 : status;
        if (sumFrom == null) {
            sumFrom = 1d;
        }
        if (sumTo == null) {
            sumTo = 9999999999d;
        }
        if (contragentDateFrom == null) {
            contragentDateFrom = MIN_DATE_TIME;
        }
        if (contragentDateTo == null) {
            contragentDateTo = MAX_DATE_TIME;
        }
        if (registratioinDateFrom == null) {
            registratioinDateFrom = MIN_DATE_TIME;
        }
        if (registrationDateTo == null) {
            registrationDateTo = MAX_DATE_TIME;
        }

        if (authorizationDateFrom == null && authorizationDateTo == null) { // Возвращаем платежи с любой датой подтверждения, в т.ч. с пустой
            return proxy.findByProductArticleLikeAndContragentIdBetweenAndSumBetweenAndStatusBetweenAndContragentTimeBetweenAndRegistrationTimeBetweenOrderByRegistrationTimeDesc
                    (productArticlePattern, contragentIdFrom, contragentIdTo, sumFrom, sumTo,
                            statusFrom, statusTo,
                            contragentDateFrom, contragentDateTo,
                            registratioinDateFrom, registrationDateTo);
        } else {
            if (authorizationDateFrom == null) {
                authorizationDateFrom = MIN_DATE_TIME;
            }
            if (authorizationDateTo == null) {
                authorizationDateTo = MAX_DATE_TIME;
            }
            return proxy.findByProductArticleLikeAndContragentIdBetweenAndSumBetweenAndStatusBetweenAndContragentTimeBetweenAndRegistrationTimeBetweenAndAuthorizationTimeBetweenOrderByRegistrationTimeDesc
                    (productArticlePattern, contragentIdFrom, contragentIdTo, sumFrom, sumTo,
                            statusFrom, statusTo,
                            contragentDateFrom, contragentDateTo,
                            registratioinDateFrom, registrationDateTo,
                            authorizationDateFrom, authorizationDateTo);
        }
    }
}
