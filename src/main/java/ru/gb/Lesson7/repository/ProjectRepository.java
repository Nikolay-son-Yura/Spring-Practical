package ru.gb.Lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson7.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
