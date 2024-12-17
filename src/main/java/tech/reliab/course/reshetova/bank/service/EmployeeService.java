package tech.reliab.course.reshetova.bank.service;

import tech.reliab.course.reshetova.bank.entity.Employee;
import tech.reliab.course.reshetova.bank.model.EmployeeModel;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(EmployeeModel employeeModel);

    void deleteEmployee(int id);

    Employee updateEmployee(int id, String name);

    Employee getEmployeeDtoById(int id);

    List<Employee> getAllEmployees();
}
