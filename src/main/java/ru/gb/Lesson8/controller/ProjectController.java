package ru.gb.Lesson8.controller;

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
import ru.gb.Lesson8.model.Project;
import ru.gb.Lesson8.model.Timesheet;
import ru.gb.Lesson8.service.ProjectService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
@Tag(name = "Projects", description = "API для работы с проектами")
public class ProjectController {

    private final ProjectService service;

    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    @Operation(summary = "Get Project", description = "Получить проект по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Project.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})

    public ResponseEntity<Project> get(@PathVariable @Parameter(description = "Идентификатор проекта") Long id) {
        Optional<Project> project = service.findById(id);
        return project.map(value -> ResponseEntity.ok().body(value)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/timesheets")
    @Operation(summary = "Get Project", description = "Получить расписание по идентификатору проекта")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Project.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<List<Timesheet>> findTimesheetsByProjectId(@PathVariable @Parameter(description = "Идентификатор проекта") Long id) {
        if (service.findById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(service.findTimesheetsByProjectId(id));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Operation(summary = "Get Project", description = "Получить все проекты ")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Project.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<List<Project>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping
    @Operation(summary = "Post Project", description = "Создать проект ")
    public ResponseEntity<Project> create(@RequestBody Project project) {
        project = service.create(project);

        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Project", description = "Удалить проект по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) })})
    public ResponseEntity<Void> delete(@PathVariable @Parameter(description = "Идентификатор проекта") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
