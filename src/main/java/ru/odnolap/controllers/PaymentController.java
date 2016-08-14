package ru.odnolap.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.odnolap.service.PaymentService;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class PaymentController {

    @Autowired
    private PaymentService service;

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public String paymentList(Model model) {
        model.addAttribute("paymentList", service.getAll());
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
        if (productArticle != null ) {
            request.setAttribute("productArticle", productArticle);
        }
        if (contragentId != null) {
            request.setAttribute("contragentId", contragentId);
        }
        if (sumFrom != null) {
            request.setAttribute("sumFrom", sumFrom/*.toString()*/);
        }
        if (sumTo != null) {
            request.setAttribute("sumTo", sumTo/*.toString()*/);
        }
        if (status != null) {
            request.setAttribute("status", status/*.toString()*/);
        }
        if (contragentTimeFrom != null) {
            request.setAttribute("contragentTimeFrom", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(contragentTimeFrom));
        }
        if (contragentTimeTo != null) {
            request.setAttribute("contragentTimeTo", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(contragentTimeTo));
        }
        if (registrationTimeFrom != null) {
            request.setAttribute("registrationTimeFrom", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(registrationTimeFrom));
        }
        if (registrationTimeTo != null) {
            request.setAttribute("registrationTimeTo", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(registrationTimeTo));
        }
        if (authorizationTimeFrom != null) {
            request.setAttribute("authorizationTimeFrom", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(authorizationTimeFrom));
        }
        if (authorizationTimeTo != null) {
            request.setAttribute("authorizationTimeTo", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(authorizationTimeTo));
        }

        model.addAttribute("paymentList",
                service.getFiltered(productArticle, contragentId, sumFrom, sumTo, status, contragentTimeFrom, contragentTimeTo,
                        registrationTimeFrom, registrationTimeTo, authorizationTimeFrom, authorizationTimeTo));
        return "payments/list";
    }

}
