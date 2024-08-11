package ru.gb.client;

import lombok.Data;


import java.time.LocalDate;

@Data

public class TimesheetResponse {

    private Long id;
    private Long projectId;
    private Long EmployeeId;
    private Integer minutes;
    private LocalDate createdAt;
}
