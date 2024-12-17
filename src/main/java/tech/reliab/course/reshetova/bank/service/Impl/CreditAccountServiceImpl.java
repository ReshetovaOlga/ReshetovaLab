package tech.reliab.course.reshetova.bank.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.reshetova.bank.entity.CreditAccount;
import tech.reliab.course.reshetova.bank.model.CreditAccountModel;
import tech.reliab.course.reshetova.bank.repository.*;
import tech.reliab.course.reshetova.bank.service.CreditAccountService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CreditAccountServiceImpl implements CreditAccountService {
    private final CreditAccountRepository creditAccountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final EmployeeRepository employeeRepository;
    private final PaymentAccountRepository paymentAccountRepository;

    @Override
    public CreditAccount createCreditAccount(CreditAccountModel creditAccountModel) {
        return creditAccountRepository.save(
                new CreditAccount(
                        userRepository.findById(creditAccountModel.getUserId()).orElseThrow(),
                        bankRepository.findById(creditAccountModel.getBankId()).orElseThrow(),
                        creditAccountModel.getStartDate(),
                        creditAccountModel.getLoanTermMonths(),
                        creditAccountModel.getInterestRate(),
                        employeeRepository.findById(creditAccountModel.getEmployeeId()).orElseThrow(),
                        paymentAccountRepository.findById(creditAccountModel.getPaymentAccountId()).orElseThrow()
                )
        );
    }

    @Override
    public void deleteCreditAccount(int id) {
        creditAccountRepository.deleteById(id);
    }

    @Override
    public CreditAccount updateCreditAccount(int id, int bankId) {
        var creditAccount = creditAccountRepository.findById(id).orElseThrow();
        creditAccount.setBank(bankRepository.findById(bankId).orElseThrow());

        return creditAccountRepository.save(creditAccount);
    }

    @Override
    public CreditAccount getCreditAccountDtoById(int id) {
        return creditAccountRepository.findById(id).orElseThrow();
    }

    @Override
    public List<CreditAccount> getAllCreditAccounts() {
        return creditAccountRepository.findAll();
    }
}
