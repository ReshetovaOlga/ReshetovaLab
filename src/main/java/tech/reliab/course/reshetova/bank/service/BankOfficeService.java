package tech.reliab.course.reshetova.bank.service;

import tech.reliab.course.reshetova.bank.entity.BankOffice;
import tech.reliab.course.reshetova.bank.model.BankOfficeModel;

import java.util.List;

public interface BankOfficeService {

    BankOffice createBankOffice(BankOfficeModel bankOfficeModel);

    void deleteBankAtm(int id);

    BankOffice updateBankOffice(int id, String name);

    BankOffice getBankDtoOfficeById(int id);

    List<BankOffice> getAllBankOffices();
}
