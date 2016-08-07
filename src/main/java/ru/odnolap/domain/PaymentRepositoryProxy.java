package ru.odnolap.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface PaymentRepositoryProxy extends JpaRepository<Payment, Integer> {

    List<Payment> findByProductArticleLikeAndContragentIdBetweenAndSumBetweenAndStatusBetweenAndContragentTimeBetweenAndRegistrationTimeBetweenAndAuthorizationTimeBetweenOrderByRegistrationTimeDesc
            (String productArticle, Integer contragentIdFrom, Integer contragentIdTo,
             Double sumFrom, Double sumTo, Integer statusFrom, Integer statusTo,
             Date contragentDateFrom, Date contragentDateTo,
             Date registratioinDateFrom, Date RegistrationDateTo,
             Date authorizationDateFrom, Date authorizationDateTo);

    List<Payment> findByProductArticleLikeAndContragentIdBetweenAndSumBetweenAndStatusBetweenAndContragentTimeBetweenAndRegistrationTimeBetweenOrderByRegistrationTimeDesc
            (String productArticle, Integer contragentIdFrom, Integer contragentIdTo,
             Double sumFrom, Double sumTo, Integer statusFrom, Integer statusTo,
             Date contragentDateFrom, Date contragentDateTo,
             Date registratioinDateFrom, Date RegistrationDateTo);

}
