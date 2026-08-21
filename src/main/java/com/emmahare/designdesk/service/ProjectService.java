package com.emmahare.designdesk.service;

import com.emmahare.designdesk.exception.ProjectNotFoundException;
import com.emmahare.designdesk.model.Project;
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
}
