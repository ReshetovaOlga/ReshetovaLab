package tech.reliab.course.reshetova.bank.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.reshetova.bank.entity.BankOffice;
import tech.reliab.course.reshetova.bank.model.BankOfficeModel;
import tech.reliab.course.reshetova.bank.repository.BankOfficeRepository;
import tech.reliab.course.reshetova.bank.repository.BankRepository;
import tech.reliab.course.reshetova.bank.service.BankOfficeService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankOfficeServiceImpl implements BankOfficeService {
    private final BankOfficeRepository bankOfficeRepository;
    private final BankRepository bankRepository;

    @Override
    public BankOffice createBankOffice(BankOfficeModel bankOfficeModel) {
        return bankOfficeRepository.save(
                new BankOffice(
                        bankOfficeModel.getName(),
                        bankOfficeModel.getAddress(),
                        bankOfficeModel.isCanPlaceAtm(),
                        bankOfficeModel.isCanIssueLoan(),
                        bankOfficeModel.isCashWithdrawal(),
                        bankOfficeModel.isCashDeposit(),
                        bankOfficeModel.getRentCost(),
                        bankRepository.findById(bankOfficeModel.getBankId()).orElseThrow()
                )
        );
    }

    @Override
    public void deleteBankAtm(int id) {
        bankOfficeRepository.deleteById(id);
    }

    @Override
    public BankOffice updateBankOffice(int id, String name) {
        var bankOffice = bankOfficeRepository.findById(id).orElseThrow();
        bankOffice.setName(name);

        return bankOfficeRepository.save(bankOffice);
    }

    @Override
    public BankOffice getBankDtoOfficeById(int id) {
        return bankOfficeRepository.findById(id).orElseThrow();
    }

    @Override
    public List<BankOffice> getAllBankOffices() {
        return bankOfficeRepository.findAll();
    }
}
