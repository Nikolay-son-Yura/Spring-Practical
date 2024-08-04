package ru.gb.Lesson7.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson7.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
