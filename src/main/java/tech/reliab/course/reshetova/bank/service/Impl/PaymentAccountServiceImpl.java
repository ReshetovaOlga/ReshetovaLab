package tech.reliab.course.reshetova.bank.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.reshetova.bank.entity.PaymentAccount;
import tech.reliab.course.reshetova.bank.entity.User;
import tech.reliab.course.reshetova.bank.model.PaymentAccountModel;
import tech.reliab.course.reshetova.bank.repository.BankRepository;
import tech.reliab.course.reshetova.bank.repository.PaymentAccountRepository;
import tech.reliab.course.reshetova.bank.repository.UserRepository;
import tech.reliab.course.reshetova.bank.service.PaymentAccountService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentAccountServiceImpl implements PaymentAccountService {
    private final PaymentAccountRepository paymentAccountRepository;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;

    @Override
    public PaymentAccount createPaymentAccount(PaymentAccountModel paymentAccountModel) {
        return paymentAccountRepository.save(
                new PaymentAccount(
                    userRepository.findById(paymentAccountModel.getUserId()).orElseThrow(),
                    bankRepository.findById(paymentAccountModel.getBankId()).orElseThrow()
                )
        );
    }

    @Override
    public void deletePaymentAccount(int id) {
        paymentAccountRepository.deleteById(id);
    }

    @Override
    public PaymentAccount updatePaymentAccount(int id, int bankId) {
        var paymentAccount = paymentAccountRepository.findById(id).orElseThrow();
        paymentAccount.setBank(bankRepository.findById(bankId).orElseThrow());

        return paymentAccountRepository.save(paymentAccount);
    }

    @Override
    public PaymentAccount getPaymentAccountDtoById(int id) {
        return paymentAccountRepository.findById(id).orElseThrow();
    }

    @Override
    public List<PaymentAccount> getAllPaymentAccounts() {
        return paymentAccountRepository.findAll();
    }
}
