package ru.gb.Lesson6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson6.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
