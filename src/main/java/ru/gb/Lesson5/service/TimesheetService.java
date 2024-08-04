package ru.gb.Lesson5.service;

import org.springframework.stereotype.Service;
import ru.gb.Lesson5.model.Timesheet;
import ru.gb.Lesson5.repository.ProjectRepository;
import ru.gb.Lesson5.repository.TimesheetRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

    public List<Timesheet> findByProject(Long id) {
        return timesheetRepository.findByProjectId(id);
    }

    public List<Timesheet> findByEmployee(Long id) {
        return timesheetRepository.findByEmployeeId(id);
    }
    public List<Timesheet> findAll() {
        return findAll(null, null);
    }

    public List<Timesheet> findAll(LocalDate createdAtBefore, LocalDate createdAtAfter) {
        if (Objects.nonNull(createdAtBefore)) {
            return timesheetRepository.findByCreatedAtBefore(createdAtBefore);
        }
        if (Objects.nonNull(createdAtAfter)) {
            return timesheetRepository.findByCreatedAtAfter(createdAtAfter);
        }
        return timesheetRepository.findAll();
    }

    public Timesheet create(Timesheet timesheet) {
        return timesheetRepository.save(timesheet);
    }

    public void delete(Long id) {
        timesheetRepository.deleteById(id);
    }


}
