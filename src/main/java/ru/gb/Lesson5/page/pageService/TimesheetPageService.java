package ru.gb.Lesson5.page.pageService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.gb.Lesson5.model.Timesheet;
import ru.gb.Lesson5.page.pageDto.TimesheetPageDto;
import ru.gb.Lesson5.service.ProjectService;
import ru.gb.Lesson5.service.TimesheetService;

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

        TimesheetPageDto timesheetPageParameters = new TimesheetPageDto();

        timesheetPageParameters.setId(String.valueOf(timesheet.getId()));
        timesheetPageParameters.setMinutes(String.valueOf(timesheet.getMinutes()));
        timesheetPageParameters.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
        timesheetPageParameters.setCreatedAt(timesheet.getCreatedAt().format(DateTimeFormatter.ISO_DATE));
        timesheetPageParameters.setProject(timesheet.getProject());
        timesheetPageParameters.setEmployee(timesheet.getEmployee());

        return timesheetPageParameters;
    }
}
