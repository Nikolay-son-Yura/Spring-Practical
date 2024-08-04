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
import ru.gb.Lesson6.model.Project;
import ru.gb.Lesson6.model.Timesheet;
import ru.gb.Lesson6.service.TimesheetService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timesheets")
@Tag(name = "Timesheets", description = "API для работы с расписанием")
public class TimesheetController {

    private final TimesheetService service;

    public TimesheetController(TimesheetService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Timesheets", description = "Получить расписание по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Timesheet.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Timesheet> get(@PathVariable @Parameter(description = "Идентификатор расписания") Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Get Timesheets", description = "Получить всё расписание")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Timesheet.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<List<Timesheet>> getAll() {
        return ResponseEntity.ok(service.findAll());

    }

    @PostMapping
    @Operation(summary = "Post Timesheets", description = "Создать расписание")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Timesheet.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        timesheet = service.create(timesheet);

        return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Timesheets", description = "Удалить расписание по его идентификатору")
    @ApiResponses({

            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор расписания") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
