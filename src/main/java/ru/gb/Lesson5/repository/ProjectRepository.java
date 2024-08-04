package ru.gb.Lesson5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson5.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
