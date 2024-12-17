package tech.reliab.course.reshetova.bank.service;

import tech.reliab.course.reshetova.bank.entity.CreditAccount;
import tech.reliab.course.reshetova.bank.model.CreditAccountModel;

import java.util.List;

public interface CreditAccountService {
    CreditAccount createCreditAccount(CreditAccountModel creditAccountModel);

    void deleteCreditAccount(int id);

    CreditAccount updateCreditAccount(int id, int bankId);

    CreditAccount getCreditAccountDtoById(int id);

    List<CreditAccount> getAllCreditAccounts();
}
