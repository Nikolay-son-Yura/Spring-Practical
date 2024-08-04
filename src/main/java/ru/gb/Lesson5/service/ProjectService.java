package ru.gb.Lesson5.service;

import org.springframework.stereotype.Service;
import ru.gb.Lesson5.model.Project;
import ru.gb.Lesson5.model.Timesheet;
import ru.gb.Lesson5.repository.ProjectRepository;
import ru.gb.Lesson5.repository.TimesheetRepository;

import java.util.List;
import java.util.NoSuchElementException;
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
        return projectRepository.findById(id);
    }

    public List<Timesheet> getTimesheets(Long id) {
        if (projectRepository.findById(id).isEmpty()) {
            throw new NoSuchElementException("Project with id = " + id + " does not exists");
        }

        return timesheetRepository.findByProjectId(id);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project create(Project project) {
        return projectRepository.save(project);
    }

    public void delete(Long id) {
        projectRepository.deleteById(id);
    }

}
