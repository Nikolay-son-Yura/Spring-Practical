package ru.gb.page.pageDto;

import lombok.Data;


@Data
public class TimesheetPageDto {

    private String id;
    private String minutes;
    private String createdAt;
    private String projectId;
    private String employeeId;


}
