package tech.reliab.course.reshetova.bank.service;

import tech.reliab.course.reshetova.bank.entity.BankAtm;
import tech.reliab.course.reshetova.bank.model.BankAtmModel;

import java.util.List;

public interface BankAtmService {
    void modelBankInfo();

    BankAtm createBankAtm(BankAtmModel bankAtmModel);

    void deleteBankAtm(int id);

    BankAtm updateBankAtm(int id, String name);

    BankAtm getBankAtmDtoById(int id);

    List<BankAtm> getAllBankAtmsByBankId(int bankId);

    List<BankAtm> getAllBankAtms();
}
