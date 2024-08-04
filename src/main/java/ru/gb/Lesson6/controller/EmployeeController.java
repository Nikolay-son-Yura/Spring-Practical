package ru.gb.Lesson6.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.Lesson6.model.Employee;
import ru.gb.Lesson6.model.Project;
import ru.gb.Lesson6.model.Timesheet;
import ru.gb.Lesson6.service.EmployeeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employee", description = "API для работы с сотрудниками")
public class EmployeeController {
    private final EmployeeService service;


    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Employee", description = "Получить сотрудника по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Employee> get(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        Optional<Employee> employee = service.findById(id);
        return employee.map(value -> ResponseEntity.status(HttpStatus.OK).body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get Employee", description = "Получить всех сотрудников")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}/timesheets")
    @Operation(summary = "Get Employee", description = "Получить расписание по идентификатору сотрудника")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<List<Timesheet>> getTimesheetsByEmployeeId(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        if (service.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(service.findTimesheetsByEmployeeId(id));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Post Employee", description = "Создать сотрудников")
    @ApiResponses({
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        employee = service.create(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Employee", description = "Удалить сотрудника по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Employee.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор сотрудника") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
