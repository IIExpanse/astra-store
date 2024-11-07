package ru.expanse.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.expanse.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
