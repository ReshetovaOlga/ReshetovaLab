package tech.reliab.course.reshetova.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.reshetova.bank.controller.BankOfficeController;
import tech.reliab.course.reshetova.bank.entity.BankOffice;
import tech.reliab.course.reshetova.bank.model.BankOfficeModel;
import tech.reliab.course.reshetova.bank.service.BankOfficeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bank-offices")
public class BankOfficeControllerImpl implements BankOfficeController {

    private final BankOfficeService bankOfficeService;

    @Override
    @PostMapping
    public ResponseEntity<BankOffice> createBankOffice(BankOfficeModel bankOfficeModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankOfficeService.createBankOffice(bankOfficeModel));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBankOffice(int id) {
        bankOfficeService.deleteBankAtm(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<BankOffice> updateBankOffice(int id, String name) {
        return ResponseEntity.ok(bankOfficeService.updateBankOffice(id, name));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<BankOffice> getBankOfficeById(int id) {
        return ResponseEntity.ok(bankOfficeService.getBankDtoOfficeById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<BankOffice>> getAllBankOffices() {
        return ResponseEntity.ok(bankOfficeService.getAllBankOffices());
    }
}
