package tech.reliab.course.reshetova.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.reliab.course.reshetova.bank.entity.*;
import tech.reliab.course.reshetova.bank.model.BankOfficeModel;
import tech.reliab.course.reshetova.bank.repository.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class BankOfficeServiceTest {

    @Mock
    private BankOfficeRepository bankOfficeRepository;

    @Mock
    private BankService bankService;

    @InjectMocks
    private BankOfficeService bankOfficeService;

    private Bank testBank;
    private BankOffice testOffice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBank = new Bank("Test Bank");
        testBank.setId(1);
        testBank.setTotalMoney(1_000_000.0);

        testOffice = new BankOffice("Office-1", "123 Main St", testBank, true, true, true, true);
        testOffice.setId(1);
        testOffice.setRentCost(500);
        testOffice.setOfficeMoney(100_000.0);
    }

    @Test
    void testCreateBankOffice() {
        // Arrange
        BankOfficeModel Model = new BankOfficeModel("Office-1", "123 Main St", true, 500.0, 1, BankOfficeStatusEnum.WORKING,
                true, true, true, 5000);

        when(bankService.getBankDtoById(1)).thenReturn(testBank);
        when(bankOfficeRepository.save(any(BankOffice.class))).thenAnswer(invocation -> {
            BankOffice office = invocation.getArgument(0);
            office.setId(1);
            return office;
        });

        // Act
        BankOffice createdOffice = bankOfficeService.createBankOffice(Model);

        // Assert
        assertThat(createdOffice).isNotNull();
        assertThat(createdOffice.getId()).isEqualTo(1);
        assertThat(createdOffice.getName()).isEqualTo("Office-1");
        assertThat(createdOffice.getBank()).isEqualTo(testBank);
        assertThat(createdOffice.getRentCost()).isBetween(0.0, 1000.0);
        assertThat(createdOffice.getOfficeMoney()).isBetween(0.0, testBank.getTotalMoney());

        verify(bankService).getBankDtoById(1);
        verify(bankOfficeRepository).save(any(BankOffice.class));
    }

    @Test
    void testGetBankOfficeById_Success() {
        // Arrange
        when(bankOfficeRepository.findById(1)).thenReturn(Optional.of(testOffice));

        // Act
        BankOffice foundOffice = bankOfficeService.getBankDtoOfficeById(1);

        // Assert
        assertThat(foundOffice).isNotNull();
        assertThat(foundOffice.getId()).isEqualTo(1);
        assertThat(foundOffice.getName()).isEqualTo("Office-1");

        verify(bankOfficeRepository).findById(1);
    }

    @Test
    void testGetBankOfficeById_NotFound() {
        // Arrange
        when(bankOfficeRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bankOfficeService.getBankDtoOfficeById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("BankOffice was not found");

        verify(bankOfficeRepository).findById(1);
    }

    @Test
    void testGetBankDtoOfficeById() {
        // Arrange
        when(bankOfficeRepository.findById(1)).thenReturn(Optional.of(testOffice));

        // Act
        BankOffice officeDto = bankOfficeService.getBankDtoOfficeById(1);

        // Assert
        assertThat(officeDto).isNotNull();
        assertThat(officeDto.getId()).isEqualTo(1);
        assertThat(officeDto.getName()).isEqualTo("Office-1");

        verify(bankOfficeRepository).findById(1);
    }

    @Test
    void testGetAllBankOffices() {
        // Arrange
        List<BankOffice> offices = List.of(testOffice);
        when(bankOfficeRepository.findAll()).thenReturn(offices);

        // Act
        List<BankOffice> result = bankOfficeService.getAllBankOffices();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Office-1");

        verify(bankOfficeRepository).findAll();
    }

    @Test
    void testUpdateBankOffice_Success() {
        // Arrange
        when(bankOfficeRepository.findById(1)).thenReturn(Optional.of(testOffice));
        when(bankOfficeRepository.save(any(BankOffice.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        BankOffice updatedOffice = bankOfficeService.updateBankOffice(1, "Updated Office");

        // Assert
        assertThat(updatedOffice).isNotNull();
        assertThat(updatedOffice.getName()).isEqualTo("Updated Office");

        verify(bankOfficeRepository).findById(1);
        verify(bankOfficeRepository).save(any(BankOffice.class));
    }

    @Test
    void testUpdateBankOffice_NotFound() {
        // Arrange
        when(bankOfficeRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> bankOfficeService.updateBankOffice(1, "Updated Office"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("BankOffice was not found");

        verify(bankOfficeRepository).findById(1);
        verify(bankOfficeRepository, never()).save(any(BankOffice.class));
    }

    @Test
    void testDeleteBankAtm() {
        // Arrange
        doNothing().when(bankOfficeRepository).deleteById(1);

        // Act
        bankOfficeService.deleteBankAtm(1);

        // Assert
        verify(bankOfficeRepository).deleteById(1);
    }
}
