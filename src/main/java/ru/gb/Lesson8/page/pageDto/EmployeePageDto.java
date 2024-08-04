package ru.gb.Lesson8.page.pageDto;

import lombok.Data;
import ru.gb.Lesson8.model.Timesheet;

import java.util.List;

@Data
public class EmployeePageDto {
    private String id;
    private String firstName;
    private String lastName;
    private List<Timesheet> timesheetList;
}
