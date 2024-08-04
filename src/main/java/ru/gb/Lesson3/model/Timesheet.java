package ru.gb.Lesson3.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Timesheet {
    private Long id;
    private Long projectId;
    private int minute;
    private LocalDateTime createdAt;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
