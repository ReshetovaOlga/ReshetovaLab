package tech.reliab.course.reshetova.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.reliab.course.reshetova.bank.entity.PaymentAccount;
import tech.reliab.course.reshetova.bank.model.PaymentAccountModel;

import java.util.List;

public interface PaymentAccountController {

    ResponseEntity<PaymentAccount> createPaymentAccount(@RequestBody PaymentAccountModel paymentAccountModel);

    ResponseEntity<Void> deletePaymentAccount(@PathVariable int id);

    ResponseEntity<PaymentAccount> updatePaymentAccount(@PathVariable int id, @RequestParam(name = "bankId") int bankId);

    ResponseEntity<PaymentAccount> getBankByPaymentAccount(@PathVariable int id);

    ResponseEntity<List<PaymentAccount>> getAllPaymentAccounts();
}
