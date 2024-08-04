package ru.gb.Lesson8.page.pageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.Lesson8.model.Timesheet;
import ru.gb.Lesson8.service.ProjectService;
import ru.gb.Lesson8.service.TimesheetService;
import ru.gb.Lesson8.page.pageDto.TimesheetPageDto;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimesheetPageService {

    private final TimesheetService timesheetService;
    private final ProjectService projectService;

    public List<TimesheetPageDto> findAll() {
        return timesheetService.findAll().stream()
                .map(this::convert)
                .toList();
    }

    public Optional<TimesheetPageDto> findById(Long id) {
        return timesheetService.findById(id) // Optional<Timesheet>
                .map(this::convert);
    }

    private TimesheetPageDto convert(Timesheet timesheet) {

//        Project project = projectService.findById(timesheet.getProjectId())
//                .orElseThrow();
        TimesheetPageDto timesheetPageParameters = new TimesheetPageDto();

        timesheetPageParameters.setId(String.valueOf(timesheet.getId()));
        timesheetPageParameters.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageParameters.setEmployeeId(String.valueOf(timesheet.getEmployeeId()));
        timesheetPageParameters.setProjectId(String.valueOf(timesheet.getProjectId()));
        timesheetPageParameters.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
        timesheetPageParameters.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));


        return timesheetPageParameters;
    }
}
