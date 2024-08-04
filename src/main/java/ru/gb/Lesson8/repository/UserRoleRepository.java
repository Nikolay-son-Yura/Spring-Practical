package ru.gb.Lesson8.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.Lesson8.model.UserRole;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    List<UserRole> findByUserId(Long userId);
}
