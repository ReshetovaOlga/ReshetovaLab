package tech.reliab.course.reshetova.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.reliab.course.reshetova.bank.entity.*;
import tech.reliab.course.reshetova.bank.model.CreditAccountModel;
import tech.reliab.course.reshetova.bank.repository.*;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CreditAccountServiceTest {

    @Mock
    private CreditAccountRepository creditAccountRepository;

    @Mock
    private BankService bankService;

    @Mock
    private UserService userService;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private PaymentAccountService paymentAccountService;

    @InjectMocks
    private CreditAccountService creditAccountService;

    private CreditAccount testCreditAccount;
    private Bank testBank;
    private User testUser;
    private Employee testEmployee;
    private PaymentAccount testPaymentAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBank = new Bank("Test Bank");
        testBank.setId(1);
        testBank.setInterestRate(10.0);

        testUser = new User();
        testUser.setId(1);
        testUser.setFullName("John Doe");

        testEmployee = new Employee();
        testEmployee.setId(1);
        testEmployee.setFullName("Jane Smith");

        testPaymentAccount = new PaymentAccount();
        testPaymentAccount.setId(1);
        testPaymentAccount.setBalance(5000.0);

        testCreditAccount = new CreditAccount(
                testUser, testBank, LocalDate.now(), 12, 10000.0, 10.0, testEmployee, testPaymentAccount
        );
        testCreditAccount.setId(1);
    }

    @Test
    void testCreateCreditAccount() {
        // Arrange
        CreditAccountModel Model = new CreditAccountModel(
                1, 1, LocalDate.now(), LocalDate.now(), 12, 10000.0, 1000, 15.0, 1, 1
        );

        when(userService.getUserDtoById(1)).thenReturn(testUser);
        when(bankService.getBankDtoById(1)).thenReturn(testBank);
        when(employeeService.getEmployeeDtoById(1)).thenReturn(testEmployee);
        when(paymentAccountService.getPaymentAccountDtoById(1)).thenReturn(testPaymentAccount);
        when(creditAccountRepository.save(any(CreditAccount.class))).thenAnswer(invocation -> {
            CreditAccount account = invocation.getArgument(0);
            account.setId(1);
            return account;
        });

        // Act
        CreditAccount createdAccount = creditAccountService.createCreditAccount(Model);

        // Assert
        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.getId()).isEqualTo(1);
        assertThat(createdAccount.getUser()).isEqualTo(testUser);
        assertThat(createdAccount.getBank()).isEqualTo(testBank);
        assertThat(createdAccount.getInterestRate()).isEqualTo(10.0); // Корректировка ставки
        assertThat(createdAccount.getMonthlyPayment()).isPositive();

        verify(creditAccountRepository).save(any(CreditAccount.class));
        verify(userService).getUserDtoById(1);
        verify(bankService).getBankDtoById(1);
        verify(employeeService).getEmployeeDtoById(1);
        verify(paymentAccountService).getPaymentAccountDtoById(1);
    }

    @Test
    void testGetCreditAccountById_Success() {
        // Arrange
        when(creditAccountRepository.findById(1)).thenReturn(Optional.of(testCreditAccount));

        // Act
        CreditAccount result = creditAccountService.getCreditAccountDtoById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        verify(creditAccountRepository).findById(1);
    }

    @Test
    void testGetCreditAccountById_NotFound() {
        // Arrange
        when(creditAccountRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> creditAccountService.getCreditAccountDtoById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("CreditAccount was not found");

        verify(creditAccountRepository).findById(1);
    }

    @Test
    void testGetAllCreditAccounts() {
        // Arrange
        when(creditAccountRepository.findAll()).thenReturn(List.of(testCreditAccount));

        // Act
        List<CreditAccount> result = creditAccountService.getAllCreditAccounts();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);

        verify(creditAccountRepository).findAll();
    }

    @Test
    void testUpdateCreditAccount_Success() {
        // Arrange
        when(creditAccountRepository.findById(1)).thenReturn(Optional.of(testCreditAccount));
        when(bankService.getBankDtoById(1)).thenReturn(testBank);
        when(creditAccountRepository.save(any(CreditAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        CreditAccount updatedAccount = creditAccountService.updateCreditAccount(1, 1);

        // Assert
        assertThat(updatedAccount).isNotNull();
        assertThat(updatedAccount.getBank()).isEqualTo(testBank);

        verify(creditAccountRepository).findById(1);
        verify(bankService).getBankDtoById(1);
        verify(creditAccountRepository).save(any(CreditAccount.class));
    }

    @Test
    void testUpdateCreditAccount_NotFound() {
        // Arrange
        when(creditAccountRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> creditAccountService.updateCreditAccount(1, 1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("CreditAccount was not found");

        verify(creditAccountRepository).findById(1);
        verify(bankService, never()).getBankDtoById(anyInt());
    }

    @Test
    void testDeleteCreditAccount() {
        // Arrange
        doNothing().when(creditAccountRepository).deleteById(1);

        // Act
        creditAccountService.deleteCreditAccount(1);

        // Assert
        verify(creditAccountRepository).deleteById(1);
    }
}
