package ru.odnolap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.odnolap.domain.PaymentRepository;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class PaymentController {
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
    private PaymentRepository repository;

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public String paymentList(Model model) {
        model.addAttribute("paymentList", repository.findAll());
        return "payments/list";
    }

    @RequestMapping(value = "/payments", method = RequestMethod.POST)
    public String filtered(@RequestParam("productArticle") String productArticle,
                           @RequestParam("contragentId") Integer contragentId,
                           @RequestParam("sumFrom") Double sumFrom,
                           @RequestParam("sumTo") Double sumTo,
                           @RequestParam("status") Integer status,
                           @RequestParam("contragentTimeFrom") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") Date contragentTimeFrom,
                           @RequestParam("contragentTimeTo") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") Date contragentTimeTo,
                           @RequestParam("registrationTimeFrom") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") Date registrationTimeFrom,
                           @RequestParam("registrationTimeTo") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") Date registrationTimeTo,
                           @RequestParam("authorizationTimeFrom") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") Date authorizationTimeFrom,
                           @RequestParam("authorizationTimeTo") @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm") Date authorizationTimeTo,
                           HttpServletRequest request, Model model) {
        Integer contragentIdFrom;
        Integer contragentIdTo;
        Integer statusFrom;
        Integer statusTo;

        if (productArticle == null || productArticle.isEmpty()) {
            productArticle = "%";
        } else {
            request.setAttribute("productArticle", productArticle);
        }
        if (contragentId == null) {
            contragentIdFrom = -2000000000;
            contragentIdTo = 2000000000;
        } else {
            contragentIdFrom = contragentId;
            contragentIdTo = contragentId;
            request.setAttribute("contragentId", contragentId.toString());
        }
        if (sumFrom == null) {
            sumFrom = 1d;
        } else {
            request.setAttribute("sumFrom", sumFrom/*.toString()*/);
        }
        if (sumTo == null) {
            sumTo = 9999999999d;
        } else {
            request.setAttribute("sumTo", sumTo/*.toString()*/);
        }
        if (status == null) {
            statusFrom = 0;
            statusTo = 1;
        } else {
            statusFrom = status;
            statusTo = status;
            request.setAttribute("status", status/*.toString()*/);
        }
        if (contragentTimeFrom == null) {
            contragentTimeFrom = MIN_DATE_TIME;
        } else {
            request.setAttribute("contragentTimeFrom", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(contragentTimeFrom));
        }
        if (contragentTimeTo == null) {
            contragentTimeTo = MAX_DATE_TIME;
        } else {
            request.setAttribute("contragentTimeTo", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(contragentTimeTo));
        }
        if (registrationTimeFrom == null) {
            registrationTimeFrom = MIN_DATE_TIME;
        } else {
            request.setAttribute("registrationTimeFrom", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(registrationTimeFrom));
        }
        if (registrationTimeTo == null) {
            registrationTimeTo = MAX_DATE_TIME;
        } else {
            request.setAttribute("registrationTimeTo", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(registrationTimeTo));
        }

        if (authorizationTimeFrom == null && authorizationTimeTo == null) {
            model.addAttribute("paymentList",
                    repository.filterWithoutRegistrationTime(productArticle, contragentIdFrom, contragentIdTo, sumFrom, sumTo, statusFrom, statusTo, contragentTimeFrom, contragentTimeTo,
                            registrationTimeFrom, registrationTimeTo));
        } else {
            if (authorizationTimeFrom == null) {
                authorizationTimeFrom = MIN_DATE_TIME;
            } else {
                request.setAttribute("authorizationTimeFrom", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(authorizationTimeFrom));
            }
            if (authorizationTimeTo == null) {
                authorizationTimeTo = MAX_DATE_TIME;
            } else {
                request.setAttribute("authorizationTimeTo", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(authorizationTimeTo));
            }
            model.addAttribute("paymentList",
                    repository.filter(productArticle, contragentIdFrom, contragentIdTo, sumFrom, sumTo, statusFrom, statusTo, contragentTimeFrom, contragentTimeTo,
                            registrationTimeFrom, registrationTimeTo, authorizationTimeFrom, authorizationTimeTo));
        }
        return "payments/list";
    }

}
