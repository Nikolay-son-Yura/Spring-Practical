package ru.gb.Lesson4.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gb.Lesson4.model.Timesheet;
import ru.gb.Lesson4.repository.ProjectRepository;
import ru.gb.Lesson4.repository.TimesheetRepository;

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

    public Optional<Timesheet> findById(Long id) {
        return timesheetRepository.findById(id);
    }

    public List<Timesheet> findAll() {
        return timesheetRepository.findAll();
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
