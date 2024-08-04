package ru.gb.Lesson5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson5.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
