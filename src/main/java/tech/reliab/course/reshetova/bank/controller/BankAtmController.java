package tech.reliab.course.reshetova.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.reliab.course.reshetova.bank.entity.BankAtm;
import tech.reliab.course.reshetova.bank.model.BankAtmModel;

import java.util.List;

public interface BankAtmController {

    ResponseEntity<BankAtm> createBankAtm(@RequestBody BankAtmModel bankAtmModel);

    ResponseEntity<Void> deleteBankAtm(@PathVariable int id);

    ResponseEntity<BankAtm> updateBankAtm(@PathVariable int id, @RequestParam(name = "name") String name);

    ResponseEntity<BankAtm> getBankAtmById(@PathVariable int id);

    ResponseEntity<List<BankAtm>> getAllBankAtmByBankId(@PathVariable int bankId);

    ResponseEntity<List<BankAtm>> getAllBankAtms();
}
