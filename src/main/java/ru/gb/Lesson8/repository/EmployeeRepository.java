package ru.gb.Lesson8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson8.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
}
