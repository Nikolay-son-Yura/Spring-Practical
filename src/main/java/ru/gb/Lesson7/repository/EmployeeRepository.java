package ru.gb.Lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson7.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
