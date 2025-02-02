package tech.reliab.course.reshetova.bank.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.reshetova.bank.controller.EmployeeController;
import tech.reliab.course.reshetova.bank.entity.Employee;
import tech.reliab.course.reshetova.bank.model.EmployeeModel;
import tech.reliab.course.reshetova.bank.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService employeeService;

    @Override
    @PostMapping
    public ResponseEntity<Employee> createEmployee(EmployeeModel employeeModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeModel));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(int id, String name) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, name));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(int id) {
        return ResponseEntity.ok(employeeService.getEmployeeDtoById(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}
