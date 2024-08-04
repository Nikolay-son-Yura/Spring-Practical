package ru.gb.Lesson8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson8.model.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);
}
