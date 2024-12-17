package tech.reliab.course.reshetova.bank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tech.reliab.course.reshetova.bank.entity.Employee;
import tech.reliab.course.reshetova.bank.model.EmployeeModel;

import java.util.List;

public interface EmployeeController {

    ResponseEntity<Employee> createEmployee(@RequestBody EmployeeModel employeeModel);

    ResponseEntity<Void> deleteEmployee(@PathVariable int id);

    ResponseEntity<Employee> updateEmployee(@PathVariable int id, @RequestParam(name = "name") String name);

    ResponseEntity<Employee> getEmployeeById(@PathVariable int id);

    ResponseEntity<List<Employee>> getAllEmployees();
}
