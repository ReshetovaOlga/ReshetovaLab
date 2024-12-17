package tech.reliab.course.reshetova.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.reliab.course.reshetova.bank.entity.*;
import tech.reliab.course.reshetova.bank.model.BankAtmModel;
import tech.reliab.course.reshetova.bank.repository.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BankAtmServiceTest {

    @Mock
    private BankAtmRepository bankAtmRepository;

    @Mock
    private BankService bankService;

    @Mock
    private BankOfficeService bankOfficeService;

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private BankAtmService bankAtmService;

    private Bank testBank;
    private BankAtm testAtm;
    private BankOffice testOffice;
    private Employee testEmployee;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBank = new Bank("Test Bank");
        testBank.setId(1);
        testBank.setTotalMoney(1_000_000.0);

        testOffice = new BankOffice();
        testOffice.setId(1);
        testOffice.setName("Main Office");

        testEmployee = new Employee();
        testEmployee.setId(1);
        testEmployee.setFullName("John Doe");

        testAtm = new BankAtm("ATM-1", "123 Test St", testBank, testOffice, testEmployee, true, true);
        testAtm.setId(1);
        testAtm.setMaintenanceCost(500);
        testAtm.setStatus(BankAtmStatusEnum.WORKING);
    }

    @Test
    void testCreateBankAtm() {
        BankAtmModel Model = new BankAtmModel("ATM-1", "123 Test St", 1, 1, 1, true, true, 0, 0);

        when(bankService.getBankDtoById(1)).thenReturn(testBank);
        when(bankOfficeService.getBankDtoOfficeById(1)).thenReturn(testOffice);
        when(employeeService.getEmployeeDtoById(1)).thenReturn(testEmployee);
        when(bankAtmRepository.save(any(BankAtm.class))).thenAnswer(invocation -> {
            BankAtm atm = invocation.getArgument(0);
            atm.setId(1);
            return atm;
        });

        BankAtm createdAtm = bankAtmService.createBankAtm(Model);

        assertThat(createdAtm).isNotNull();
        assertThat(createdAtm.getId()).isEqualTo(1);
        assertThat(createdAtm.getName()).isEqualTo("ATM-1");
        assertThat(createdAtm.getBank()).isEqualTo(testBank);
        assertThat(createdAtm.getStatus()).isIn(BankAtmStatusEnum.values());

        verify(bankAtmRepository).save(any(BankAtm.class));
        verify(bankService).getBankDtoById(1);
        verify(bankOfficeService).getBankDtoOfficeById(1);
        verify(employeeService).getEmployeeDtoById(1);
    }

    @Test
    void testGetBankAtmById() {
        when(bankAtmRepository.findById(1)).thenReturn(Optional.of(testAtm));

        BankAtm result = bankAtmService.getBankAtmDtoById(1);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("ATM-1");

        verify(bankAtmRepository).findById(1);
    }

    @Test
    void testGetBankAtmById_NotFound() {
        when(bankAtmRepository.findById(1)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bankAtmService.getBankAtmDtoById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("BankAtm was not found");

        verify(bankAtmRepository).findById(1);
    }

    @Test
    void testGetAllBankAtms() {
        when(bankAtmRepository.findAll()).thenReturn(List.of(testAtm));

        List<BankAtm> result = bankAtmService.getAllBankAtms();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("ATM-1");

        verify(bankAtmRepository).findAll();
    }

    @Test
    void testUpdateBankAtm() {
        when(bankAtmRepository.findById(1)).thenReturn(Optional.of(testAtm));
        when(bankAtmRepository.save(any(BankAtm.class))).thenReturn(testAtm);

        BankAtm updatedAtm = bankAtmService.updateBankAtm(1, "Updated ATM");

        assertThat(updatedAtm).isNotNull();
        assertThat(updatedAtm.getName()).isEqualTo("Updated ATM");

        verify(bankAtmRepository).findById(1);
        verify(bankAtmRepository).save(any(BankAtm.class));
    }

    @Test
    void testDeleteBankAtm() {
        doNothing().when(bankAtmRepository).deleteById(1);

        bankAtmService.deleteBankAtm(1);

        verify(bankAtmRepository).deleteById(1);
    }

    @Test
    void testGetAllBankAtmsByBankId() {
        when(bankAtmRepository.findAllByBankId(1)).thenReturn(List.of(testAtm));

        List<BankAtm> result = bankAtmService.getAllBankAtmsByBankId(1);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getBank()).isEqualTo(testBank);

        verify(bankAtmRepository).findAllByBankId(1);
    }
}
