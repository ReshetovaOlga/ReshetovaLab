package tech.reliab.course.reshetova.bank.service;

import tech.reliab.course.reshetova.bank.entity.PaymentAccount;
import tech.reliab.course.reshetova.bank.model.PaymentAccountModel;

import java.util.List;

public interface PaymentAccountService {
    PaymentAccount createPaymentAccount(PaymentAccountModel paymentAccountModel);

    void deletePaymentAccount(int id);

    PaymentAccount updatePaymentAccount(int id, int bankId);

    PaymentAccount getPaymentAccountDtoById(int id);

    List<PaymentAccount> getAllPaymentAccounts();
}
