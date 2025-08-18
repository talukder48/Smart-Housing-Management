package app.hm.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import app.hm.entity.Project;
import app.hm.entity.ProjectMember;
import app.hm.entity.User;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, Long> {
    List<ProjectMember> findByUser(User user);
    boolean existsByUserAndProject(User user, Project project);
}
