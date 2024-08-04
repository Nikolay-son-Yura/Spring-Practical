package ru.gb.Lesson8.service;

import org.springframework.stereotype.Service;
import ru.gb.Lesson8.model.Project;
import ru.gb.Lesson8.model.Timesheet;
import ru.gb.Lesson8.repository.ProjectRepository;
import ru.gb.Lesson8.repository.TimesheetRepository;

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

    public List<Timesheet> findTimesheetsByProjectId(Long id){
        List<Timesheet> timesheetsByProjectId = new ArrayList<>();
        for (Timesheet timesheet : timesheetRepository.findAll()) {
            if(timesheet.getProjectId().equals(id)){
                timesheetsByProjectId.add(timesheet);
            }
        }
        return timesheetsByProjectId;
    }
    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
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
