package app.hm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.hm.entity.Project;
import app.hm.entity.User;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwner(User owner);
    List<Project> findByMembersUser(User user);
}