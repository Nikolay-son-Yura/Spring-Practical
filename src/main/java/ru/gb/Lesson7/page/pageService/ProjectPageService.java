package ru.gb.Lesson7.page.pageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.Lesson7.model.Project;
import ru.gb.Lesson7.service.ProjectService;
import ru.gb.Lesson7.service.TimesheetService;
import ru.gb.Lesson7.page.pageDto.ProjectPageDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectPageService {
    private final TimesheetService timesheetService;
    private final ProjectService projectService;

    public List<ProjectPageDto> findAll() {
        return projectService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    public Optional<ProjectPageDto> findById(Long id) {
        return projectService.findById(id) // Optional<Timesheet>
                .map(this::convert);
    }

    public ProjectPageDto convert(Project project) {

        ProjectPageDto projectPageDto = new ProjectPageDto();
        projectPageDto.setId(String.valueOf(project.getId()));
        projectPageDto.setName(project.getName());
        return projectPageDto;
    }

}
