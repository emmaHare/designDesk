package com.emmahare.designdesk.service;

import com.emmahare.designdesk.exception.ProjectNotFoundException;
import com.emmahare.designdesk.model.Project;
import com.emmahare.designdesk.model.ProjectStatus;
import com.emmahare.designdesk.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Project findById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException(id));
    }

    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public Project update(Long id, Project updatedProject) {
        Project existingProject = findById(id);

        existingProject.setTitle(updatedProject.getTitle());
        existingProject.setType(updatedProject.getType());
        existingProject.setClient(updatedProject.getClient());
        existingProject.setDeadline(updatedProject.getDeadline());
        existingProject.setPrice(updatedProject.getPrice());
        existingProject.setStatus(updatedProject.getStatus());
        existingProject.setDescription(updatedProject.getDescription());

        return projectRepository.save(existingProject);
    }

    public Project markAsFinished(Long id) {
        Project project = findById(id);

        project.setStatus(ProjectStatus.FINISHED);

        return projectRepository.save(project);
    }

    public List<Project> findByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status);
    }

    public List<Project> findByClientId(Long clientId) {
        return projectRepository.findByClientId(clientId);
    }
}
