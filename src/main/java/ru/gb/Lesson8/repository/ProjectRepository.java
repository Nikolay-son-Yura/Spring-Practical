package ru.gb.Lesson8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson8.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
