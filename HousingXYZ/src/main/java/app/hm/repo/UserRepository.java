package app.hm.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.hm.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}



