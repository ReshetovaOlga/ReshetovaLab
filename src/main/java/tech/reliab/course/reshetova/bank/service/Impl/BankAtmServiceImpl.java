package tech.reliab.course.reshetova.bank.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.reshetova.bank.entity.*;
import tech.reliab.course.reshetova.bank.model.BankAtmModel;
import tech.reliab.course.reshetova.bank.repository.*;
import tech.reliab.course.reshetova.bank.service.BankAtmService;

import java.util.List;
import java.util.Scanner;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankAtmServiceImpl implements BankAtmService {
    private static final int BANKS_COUNT = 5;
    private final UserRepository userRepository;
    private final BankRepository bankRepository;
    private final BankOfficeRepository bankOfficeRepository;
    private final EmployeeRepository employeeRepository;
    private final BankAtmRepository bankAtmRepository;
    private final PaymentAccountRepository paymentAccountRepository;
    private final CreditAccountRepository creditAccountRepository;

    @Override
    public void modelBankInfo() {
        var banks = bankRepository.findAll();
        for (var bank : banks) {
            log.info(bank.toString());
        }

        var scanner = new Scanner(System.in);
        log.info("Введите ID банка");
        int id = scanner.nextInt();
        var bank = bankRepository.findById(id);
        if (bank.isEmpty()) {
            log.warn("Банк не найден");
            return;
        }

        log.info("[[ Информация о банке ]]");
        log.info(bank.get().toString());
        log.info(bankAtmRepository.findAllByBankId(bank.get().getId()).toString());
        log.info(bankOfficeRepository.findAllByBankId(bank.get().getId()).toString());
        log.info(employeeRepository.findAllByBankId(bank.get().getId()).toString());
        log.info(userRepository.findAllByBanksId(bank.get().getId()).toString());
    }

    @Override
    public BankAtm createBankAtm(BankAtmModel bankAtmModel) {
        return bankAtmRepository.save(
                new BankAtm(
                        bankAtmModel.getName(),
                        bankAtmModel.getAddress(),
                        bankRepository.findById(bankAtmModel.getBankId()).orElseThrow(),
                        bankOfficeRepository.findById(bankAtmModel.getLocationId()).orElseThrow(),
                        employeeRepository.findById(bankAtmModel.getEmployeeId()).orElseThrow(),
                        bankAtmModel.isCashWithdrawal(),
                        bankAtmModel.isCashDeposit(),
                        bankAtmModel.getMaintenanceCost()

            )
        );
    }

    @Override
    public void deleteBankAtm(int id) {
        bankAtmRepository.deleteById(id);
    }

    @Override
    public BankAtm updateBankAtm(int id, String name) {
        var bankAtm = bankAtmRepository.findById(id).orElseThrow();
        bankAtm.setName(name);

        return bankAtmRepository.save(bankAtm);
    }

    @Override
    public BankAtm getBankAtmDtoById(int id) {
        return bankAtmRepository.findById(id).orElseThrow();
    }

    @Override
    public List<BankAtm> getAllBankAtmsByBankId(int bankId) {
        return bankAtmRepository.findAllByBankId(bankId);
    }

    @Override
    public List<BankAtm> getAllBankAtms() {
        return bankAtmRepository.findAll();
    }
}
