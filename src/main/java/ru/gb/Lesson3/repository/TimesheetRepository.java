package ru.gb.Lesson3.repository;

import org.springframework.stereotype.Repository;
import ru.gb.Lesson3.model.Timesheet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TimesheetRepository {

    private static Long sequence = 1L;

    private final List<Timesheet> timesheets = new ArrayList<>();

    public Optional<Timesheet> getById(Long id) {
        return timesheets.stream().filter(it -> Objects.equals(it.getId(), id)).findFirst();
    }

    public List<Timesheet> getAll() {
        return List.copyOf(timesheets);
    }

    public Timesheet create(Timesheet timesheet) {
        timesheet.setId(sequence++);
        timesheet.setCreatedAt(LocalDateTime.now());
        timesheets.add(timesheet);
        return timesheet;
    }

    public void delete(Long id) {
        timesheets.stream().filter(it -> Objects.equals(it.getId(), id)).findFirst().ifPresent(timesheets::remove);
    }
}
