package ru.gb.Lesson5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.Lesson5.model.Employee;
import ru.gb.Lesson5.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> get(@PathVariable Long id) {
        Optional<Employee> employee = service.findById(id);
        return employee.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employee = service.create(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
