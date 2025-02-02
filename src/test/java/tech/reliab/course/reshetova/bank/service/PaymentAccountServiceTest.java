package tech.reliab.course.reshetova.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.reliab.course.reshetova.bank.entity.*;
import tech.reliab.course.reshetova.bank.model.PaymentAccountModel;
import tech.reliab.course.reshetova.bank.repository.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class PaymentAccountServiceTest {

    @Mock
    private PaymentAccountRepository paymentAccountRepository;

    @Mock
    private UserService userService;

    @Mock
    private BankService bankService;

    @InjectMocks
    private PaymentAccountService paymentAccountService;

    private PaymentAccount testPaymentAccount;
    private User testUser;
    private Bank testBank;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User("John Doe", null, "Engineer");
        testUser.setId(1);

        testBank = new Bank("Test Bank");
        testBank.setId(1);

        testPaymentAccount = new PaymentAccount(testUser, testBank);
        testPaymentAccount.setId(1);
    }

    @Test
    void testCreatePaymentAccount() {
        // Arrange
        PaymentAccountModel Model = new PaymentAccountModel(1, 1, 50000);

        when(userService.getUserById(1)).thenReturn(testUser);
        when(bankService.getBankById(1)).thenReturn(testBank);
        when(paymentAccountRepository.save(any(PaymentAccount.class))).thenAnswer(invocation -> {
            PaymentAccount paymentAccount = invocation.getArgument(0);
            paymentAccount.setId(1);
            return paymentAccount;
        });

        // Act
        PaymentAccount createdAccount = paymentAccountService.createPaymentAccount(Model);

        // Assert
        assertThat(createdAccount).isNotNull();
        assertThat(createdAccount.getId()).isEqualTo(1);
        assertThat(createdAccount.getUser()).isEqualTo(testUser);
        assertThat(createdAccount.getBank()).isEqualTo(testBank);

        verify(userService).getUserDtoById(1);
        verify(bankService).getBankDtoById(1);
        verify(paymentAccountRepository).save(any(PaymentAccount.class));
    }

    @Test
    void testGetPaymentAccountById_Success() {
        // Arrange
        when(paymentAccountRepository.findById(1)).thenReturn(Optional.of(testPaymentAccount));

        // Act
        PaymentAccount result = paymentAccountService.getPaymentAccountDtoById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getUser()).isEqualTo(testUser);

        verify(paymentAccountRepository).findById(1);
    }

    @Test
    void testGetPaymentAccountById_NotFound() {
        // Arrange
        when(paymentAccountRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> paymentAccountService.getPaymentAccountDtoById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("PaymentAccount was not found");

        verify(paymentAccountRepository).findById(1);
    }

    @Test
    void testGetPaymentAccountDtoById() {
        // Arrange
        when(paymentAccountRepository.findById(1)).thenReturn(Optional.of(testPaymentAccount));

        // Act
        PaymentAccount result = paymentAccountService.getPaymentAccountDtoById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        verify(paymentAccountRepository).findById(1);
    }

    @Test
    void testGetAllPaymentAccounts() {
        // Arrange
        when(paymentAccountRepository.findAll()).thenReturn(List.of(testPaymentAccount));

        // Act
        List<PaymentAccount> result = paymentAccountService.getAllPaymentAccounts();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1);

        verify(paymentAccountRepository).findAll();
    }

    @Test
    void testUpdatePaymentAccount_Success() {
        // Arrange
        when(paymentAccountRepository.findById(1)).thenReturn(Optional.of(testPaymentAccount));
        when(bankService.getBankDtoById(2)).thenReturn(new Bank("Updated Bank"));
        when(paymentAccountRepository.save(any(PaymentAccount.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        PaymentAccount updatedAccount = paymentAccountService.updatePaymentAccount(1, 2);

        // Assert
        assertThat(updatedAccount).isNotNull();
        assertThat(updatedAccount.getBank().getName()).isEqualTo("Updated Bank");

        verify(paymentAccountRepository).findById(1);
        verify(bankService).getBankDtoById(2);
        verify(paymentAccountRepository).save(any(PaymentAccount.class));
    }

    @Test
    void testUpdatePaymentAccount_NotFound() {
        // Arrange
        when(paymentAccountRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> paymentAccountService.updatePaymentAccount(1, 2))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("PaymentAccount was not found");

        verify(paymentAccountRepository).findById(1);
        verify(bankService, never()).getBankDtoById(anyInt());
    }

    @Test
    void testDeletePaymentAccount() {
        // Arrange
        doNothing().when(paymentAccountRepository).deleteById(1);

        // Act
        paymentAccountService.deletePaymentAccount(1);

        // Assert
        verify(paymentAccountRepository).deleteById(1);
    }
}