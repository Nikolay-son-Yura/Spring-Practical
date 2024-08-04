package ru.gb.Lesson7.page.pageDto;

import lombok.Data;
import ru.gb.Lesson7.model.Employee;
import ru.gb.Lesson7.model.Project;

@Data
public class TimesheetPageDto {

    private String id;
    private String minutes;
    private String createdAt;
    private Project project;
    private Employee employee;


}
