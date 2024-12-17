package tech.reliab.course.reshetova.bank.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tech.reliab.course.reshetova.bank.entity.*;
import tech.reliab.course.reshetova.bank.model.EmployeeModel;
import tech.reliab.course.reshetova.bank.repository.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private BankService bankService;

    @Mock
    private BankOfficeService bankOfficeService;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee testEmployee;
    private Bank testBank;
    private BankOffice testBankOffice;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBank = new Bank("Test Bank");
        testBank.setId(1);

        testBankOffice = new BankOffice();
        testBankOffice.setId(1);
        testBankOffice.setName("Main Office");

        testEmployee = new Employee("John Doe", LocalDate.of(1999, 1, 1), "Manager", false, true, testBank, testBankOffice, 50000.0);
        testEmployee.setId(1);
    }

    @Test
    void testCreateEmployee() {
        // Arrange
        EmployeeModel Model = new EmployeeModel(
                "John Doe", LocalDate.of(1999, 1, 1), "Manager", 1, true, 1, true, 50000.0
        );

        when(bankService.getBankDtoById(1)).thenReturn(testBank);
        when(bankOfficeService.getBankDtoOfficeById(1)).thenReturn(testBankOffice);
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> {
            Employee employee = invocation.getArgument(0);
            employee.setId(1);
            return employee;
        });

        // Act
        Employee createdEmployee = employeeService.createEmployee(Model);

        // Assert
        assertThat(createdEmployee).isNotNull();
        assertThat(createdEmployee.getId()).isEqualTo(1);
        assertThat(createdEmployee.getFullName()).isEqualTo("John Doe");
        assertThat(createdEmployee.getBank()).isEqualTo(testBank);
        assertThat(createdEmployee.getBankOffice()).isEqualTo(testBankOffice);
        assertThat(createdEmployee.getSalary()).isEqualTo(50000.0);

        verify(bankService).getBankDtoById(1);
        verify(bankOfficeService).getBankDtoOfficeById(1);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testGetEmployeeById_Success() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));

        // Act
        Employee result = employeeService.getEmployeeDtoById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getFullName()).isEqualTo("John Doe");

        verify(employeeRepository).findById(1);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> employeeService.getEmployeeDtoById(1))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Employee was not found");

        verify(employeeRepository).findById(1);
    }

    @Test
    void testGetEmployeeDtoById() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));

        // Act
        Employee result = employeeService.getEmployeeDtoById(1);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);

        verify(employeeRepository).findById(1);
    }

    @Test
    void testGetAllEmployees() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(List.of(testEmployee));

        // Act
        List<Employee> result = employeeService.getAllEmployees();

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFullName()).isEqualTo("John Doe");

        verify(employeeRepository).findAll();
    }

    @Test
    void testUpdateEmployee_Success() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.of(testEmployee));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Employee updatedEmployee = employeeService.updateEmployee(1, "Jane Doe");

        // Assert
        assertThat(updatedEmployee).isNotNull();
        assertThat(updatedEmployee.getFullName()).isEqualTo("Jane Doe");

        verify(employeeRepository).findById(1);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        // Arrange
        when(employeeRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> employeeService.updateEmployee(1, "Jane Doe"))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Employee was not found");

        verify(employeeRepository).findById(1);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        // Arrange
        doNothing().when(employeeRepository).deleteById(1);

        // Act
        employeeService.deleteEmployee(1);

        // Assert
        verify(employeeRepository).deleteById(1);
    }
}