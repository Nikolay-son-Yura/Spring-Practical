package ru.gb.Lesson4.pageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.Lesson4.model.Project;
import ru.gb.Lesson4.page.pageDto.ProjectPageDto;
import ru.gb.Lesson4.service.ProjectService;
import ru.gb.Lesson4.service.TimesheetService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectPageService {
    private final TimesheetService timesheetService;
    private final ProjectService projectService;

    public List<ProjectPageDto> findAll() {
        return projectService.finddAll().stream()
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
