package ru.gb.Lesson3.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.Lesson3.model.Timesheet;
import ru.gb.Lesson3.service.TimesheetService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/timesheets")
public class TimesheetController {

    private final TimesheetService service;

    public TimesheetController(TimesheetService service) {
        this.service = service;
    }

    @GetMapping("{id}")
    public ResponseEntity<Timesheet> get(@PathVariable Long id) {
        Optional<Timesheet> ts = service.getById(id);
        if (ts.isEmpty()) {
            return ResponseEntity.ok().body(ts.get());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Timesheet>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping
    public ResponseEntity<Timesheet> create(@RequestBody Timesheet timesheet) {
        timesheet = service.create(timesheet);

        return ResponseEntity.status(HttpStatus.CREATED).body(timesheet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
