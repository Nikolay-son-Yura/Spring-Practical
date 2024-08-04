package ru.gb.Lesson5.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;


@Data
@Entity
@Table(name = "timesheet")
public class Timesheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private Integer minutes;
    private LocalDate createdAt;

    @ManyToOne
    private Employee employee;

    @ManyToOne
    private Project project;


}