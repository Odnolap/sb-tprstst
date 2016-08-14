package ru.odnolap.service;

import ru.odnolap.domain.Payment;

import java.util.Collection;
import java.util.Date;

public interface PaymentService {

    Payment save(Payment payment) ;

    Date confirm(Payment payment, Double sum);

    Payment get(Integer id);

    Collection<Payment> getAll();

    Collection<Payment> getFiltered(String productArticle, Integer contragentId,
                                    Double sumFrom, Double sumTo, Integer status,
                                    Date contragentDateFrom, Date contragentDateTo,
                                    Date registratioinDateFrom, Date RegistrationDateTo,
                                    Date authorizationDateFrom, Date authorizationDateTo);
}
