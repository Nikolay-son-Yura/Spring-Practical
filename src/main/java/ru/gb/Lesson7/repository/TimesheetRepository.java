package ru.gb.Lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson7.model.Timesheet;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    List<Timesheet> findByCreatedAtAfter(LocalDate dataAfter);

    List<Timesheet> findByCreatedAtBefore(LocalDate dataBefore);



}
