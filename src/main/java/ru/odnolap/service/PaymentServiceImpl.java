package ru.odnolap.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.odnolap.domain.Payment;
import ru.odnolap.domain.PaymentRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Autowired
    private PaymentRepository repository;

    @Override
    public Payment save(Payment payment) {
        if (payment == null
                || payment.getProductArticle() == null
                || payment.getContragentId() == null
                || payment.getContragentTime() == null
                || payment.getProductArticle().isEmpty()
                || payment.getProductArticle().length() > 100
                || payment.getContragentId() % 2 == 1) {
            throw new RuntimeException("Saving error!");
        }
        if (payment.getSum() == null) {
            // Здесь должна доставаться сумма платежа по артиклу, но мы сделаем ее случайной величиной от 100 до 100 000
            BigDecimal bd = new BigDecimal(ThreadLocalRandom.current().nextDouble(100d, 100000d));
            payment.setSum(bd.setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        if (payment.getRegistrationTime() == null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.MILLISECOND, 0);
            payment.setRegistrationTime(calendar.getTime());
        }
        return repository.save(payment);
    }

    @Override
    public Date confirm(Payment payment, Double sum) {
        if (sum == null
                || payment == null
                || payment.getSum() == null
                || Math.abs(sum - payment.getSum()) > 0.0001
                || payment.getStatus() == 1) {
            throw new RuntimeException("Confirm error!");
        }
        payment.setStatus(1);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        Date authorizationTime = calendar.getTime();
        payment.setAuthorizationTime(authorizationTime);
        repository.save(payment);
        return authorizationTime;
    }

    @Override
    public Payment get(Integer id) {
        Assert.notNull(id, "Payment id must not be null");
        return repository.get(id);
    }

    @Override
    public Collection<Payment> getAll() {
        return repository.getAll();
    }

    @Override
    public Collection<Payment> getFiltered(String productArticle, Integer contragentId,
                                           Double sumFrom, Double sumTo, Integer status,
                                           Date contragentDateFrom, Date contragentDateTo,
                                           Date registratioinDateFrom, Date registrationDateTo,
                                           Date authorizationDateFrom, Date authorizationDateTo) {
        return repository.getFiltered(productArticle, contragentId, sumFrom, sumTo, status,
                contragentDateFrom, contragentDateTo, registratioinDateFrom, registrationDateTo,
                authorizationDateFrom, authorizationDateTo);
    }
}
