package tech.reliab.course.reshetova.bank.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.reliab.course.reshetova.bank.entity.Employee;
import tech.reliab.course.reshetova.bank.model.EmployeeModel;
import tech.reliab.course.reshetova.bank.repository.BankOfficeRepository;
import tech.reliab.course.reshetova.bank.repository.BankRepository;
import tech.reliab.course.reshetova.bank.repository.EmployeeRepository;
import tech.reliab.course.reshetova.bank.service.EmployeeService;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final BankRepository bankRepository;
    private final BankOfficeRepository bankOfficeRepository;

    @Override
    public Employee createEmployee(EmployeeModel employeeModel) {
        return employeeRepository.save(
                new Employee(
                        employeeModel.getFullName(),
                        employeeModel.getBirthDate(),
                        employeeModel.getPosition(),
                        bankRepository.findById(employeeModel.getBankId()).orElseThrow(),
                        employeeModel.isRemoteWork(),
                        bankOfficeRepository.findById(employeeModel.getBankOfficeId()).orElseThrow(),
                        employeeModel.isCanIssueLoans(),
                        employeeModel.getSalary()
                )
        );
    }

    @Override
    public void deleteEmployee(int id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public Employee updateEmployee(int id, String name) {
        var employee = employeeRepository.findById(id).orElseThrow();
        employee.setFullName(name);

        return employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeDtoById(int id) {
        return employeeRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
