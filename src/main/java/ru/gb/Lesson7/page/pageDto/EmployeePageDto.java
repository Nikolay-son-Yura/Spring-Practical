package ru.gb.Lesson7.page.pageDto;

import lombok.Data;
import ru.gb.Lesson7.model.Timesheet;

import java.util.List;

@Data
public class EmployeePageDto {
    private String id;
    private String firstName;
    private String lastName;
    private List<Timesheet> timesheetList;
}
