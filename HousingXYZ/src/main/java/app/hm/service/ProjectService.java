package app.hm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.hm.entity.Project;
import app.hm.entity.ProjectMember;
import app.hm.entity.User;
import app.hm.enums.MemberRole;
import app.hm.enums.ProjectStatus;
import app.hm.repo.ProjectMemberRepository;
import app.hm.repo.ProjectRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
    @Autowired ProjectRepository projectRepository;
    @Autowired ProjectMemberRepository projectMemberRepository;
    
    public List<Project> getProjectsOwnedBy(User owner) {
        return projectRepository.findByOwner(owner);
    }
    
    public List<Project> getProjectsWhereMember(User user) {
        return projectRepository.findByMembersUser(user);
    }
    
    public Project createProject(Project project, User owner) {
        project.setOwner(owner);
        project.setStatus(ProjectStatus.PLANNING);
        
        Project savedProject = projectRepository.save(project);
        
        // Add owner as manager
        addMember(savedProject, owner, MemberRole.MANAGER);
        
        return savedProject;
    }
    
    public void addMember(Project project, User user, MemberRole role) {
        ProjectMember member = new ProjectMember();
        member.setProject(project);
        member.setUser(user);
        member.setRole(role);
        
        projectMemberRepository.save(member);
    }
}