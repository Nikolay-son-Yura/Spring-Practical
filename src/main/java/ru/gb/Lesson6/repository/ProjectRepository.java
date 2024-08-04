package ru.gb.Lesson6.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson6.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
