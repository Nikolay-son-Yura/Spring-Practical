package ru.gb.Lesson5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson5.model.Timesheet;

import java.time.LocalDate;
import java.util.List;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

    List<Timesheet> findByProjectId(Long projectId);

    List<Timesheet> findByEmployeeId(Long EmployeeId);

    List<Timesheet> findByCreatedAtAfter(LocalDate dataAfter);

    List<Timesheet> findByCreatedAtBefore(LocalDate dataBefore);


//    @Query("select t from Timesheet t where t.projectId = :projectId order by t.createdAt desc")
//    List<Timesheet> findByProjectId(Long projectId);
}
