package tech.reliab.course.reshetova.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.reshetova.bank.controller.CreditAccountController;
import tech.reliab.course.reshetova.bank.entity.CreditAccount;
import tech.reliab.course.reshetova.bank.model.CreditAccountModel;
import tech.reliab.course.reshetova.bank.service.CreditAccountService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/credit-accounts")
public class CreditAccountControllerImpl implements CreditAccountController {

    private final CreditAccountService creditAccountService;

    @Override
    @PostMapping
    public ResponseEntity<CreditAccount> createCreditAccount(CreditAccountModel creditAccountModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(creditAccountService.createCreditAccount(creditAccountModel));
    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> deleteCreditAccount(int id) {
        creditAccountService.deleteCreditAccount(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<CreditAccount> updateCreditAccount(int id, int bankId) {
        return ResponseEntity.ok(creditAccountService.updateCreditAccount(id, bankId));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<CreditAccount> getBankByCreditAccount(int id) {
        return ResponseEntity.ok(creditAccountService.getCreditAccountDtoById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<CreditAccount>> getAllCreditAccounts() {
        return ResponseEntity.ok(creditAccountService.getAllCreditAccounts());
    }
}
