package ru.gb.Lesson5.page.pageDto;

import lombok.Data;
import ru.gb.Lesson5.model.Timesheet;
import ru.gb.Lesson5.model.Project;

import java.util.List;

@Data
public class EmployeePageDto {

    private String id;
    private String name;
    private List<Timesheet> timesheets;
    private List<Project> projects;


}
