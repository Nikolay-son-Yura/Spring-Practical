package ru.gb.Lesson4.page.pageDto;

import lombok.Data;

@Data
public class TimesheetPageDto {
    private String projectName;
    private String projectId;
    private String id;
    private String minutes;
    private String createdAt;
}
