package ru.gb.page.pageDto;

import lombok.Data;
import ru.gb.client.TimesheetResponse;


import java.util.List;


@Data
public class EmployeePageDto {
    private String id;
    private String firstName;
    private String lastName;
    private List<TimesheetResponse> timesheetList;
//    private List<Timesheet> timesheetList;
}
