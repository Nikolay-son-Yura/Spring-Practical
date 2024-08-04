package ru.gb.Lesson4.service;

import org.springframework.stereotype.Service;
import ru.gb.Lesson4.model.Project;
import ru.gb.Lesson4.model.Timesheet;
import ru.gb.Lesson4.repository.ProjectRepository;
import ru.gb.Lesson4.repository.TimesheetRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final TimesheetRepository timesheetRepository;

    public ProjectService(ProjectRepository projectRepository, TimesheetRepository timesheetRepository) {
        this.projectRepository = projectRepository;
        this.timesheetRepository = timesheetRepository;
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.getById(id);
    }

    public List<Timesheet> getTimesheets(Long id) {
        List<Timesheet> timesheetsByProjectId = new ArrayList<>();
        for (Timesheet timesheet : timesheetRepository.findAll()) {
            if (timesheet.getProjectId().equals(id)) {
                timesheetsByProjectId.add(timesheet);
            }
        }
        return timesheetsByProjectId;
    }

    public List<Project> finddAll() {
        return projectRepository.getAll();
    }

    public Project create(Project project) {
        return projectRepository.create(project);
    }

    public void delete(Long id) {
        projectRepository.delete(id);
    }

}
