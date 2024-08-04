package ru.gb.Lesson3.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gb.Lesson3.model.Timesheet;
import ru.gb.Lesson3.repository.ProjectRepository;
import ru.gb.Lesson3.repository.TimesheetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TimesheetService {
    private final TimesheetRepository timesheetRepository;
    private final ProjectRepository projectRepository;

    public TimesheetService(TimesheetRepository timesheetRepository, ProjectRepository projectRepository) {
        this.timesheetRepository = timesheetRepository;
        this.projectRepository = projectRepository;
    }

    public Optional<Timesheet> getById(Long id) {
        return timesheetRepository.getById(id);
    }

    public List<Timesheet> getAll() {
        return timesheetRepository.getAll();
    }

    public Timesheet create(Timesheet timesheet) {
        if (projectRepository.getById(timesheet.getProjectId()).isPresent()) {
            return timesheetRepository.create(timesheet);
        }
        return (Timesheet) ResponseEntity.badRequest();
    }

    public void delete(Long id) {
        timesheetRepository.delete(id);
    }
}
