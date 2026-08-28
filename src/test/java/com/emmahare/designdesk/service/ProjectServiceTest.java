package com.emmahare.designdesk.service;

import com.emmahare.designdesk.exception.ProjectNotFoundException;
import com.emmahare.designdesk.model.Project;
import com.emmahare.designdesk.model.ProjectStatus;
import com.emmahare.designdesk.repository.ProjectRepository;
import com.emmahare.designdesk.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    private ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ProjectService(projectRepository);
    }

    @Test
    void findAllShouldReturnAllProjects() {
        Project project1 = new Project();
        project1.setTitle("Logo Design");

        Project project2 = new Project();
        project2.setTitle("Album Cover");

        List<Project> projects = List.of(project1, project2);

        when(projectRepository.findAll()).thenReturn(projects);

        List<Project> result = projectService.findAll();

        assertEquals(2, result.size());
        assertEquals("Logo Design", result.get(0).getTitle());
        assertEquals("Album Cover", result.get(1).getTitle());
    }

    @Test
    void findByIdShouldReturnProject() {
        Project project = new Project();
        project.setTitle("Logo Design");

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        Project result = projectService.findById(1L);

        assertEquals("Logo Design", result.getTitle());
    }

    @Test
    void findByIdShouldThrowExceptionWhenProjectDoesNotExist() {
        when(projectRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProjectNotFoundException.class,
                () -> projectService.findById(99L)
        );
    }

    @Test
    void saveShouldReturnSavedProject() {
        Project project = new Project();
        project.setTitle("New Project");

        when(projectRepository.save(project))
                .thenReturn(project);

        Project result = projectService.save(project);

        assertEquals("New Project", result.getTitle());
    }

    @Test
    void updateShouldUpdateExistingProject() {
        Project existingProject = new Project();
        existingProject.setTitle("Old Title");
        existingProject.setDescription("Old Description");

        Project updatedProject = new Project();
        updatedProject.setTitle("New Title");
        updatedProject.setDescription("New Description");

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(existingProject));

        when(projectRepository.save(existingProject))
                .thenReturn(existingProject);

        Project result = projectService.update(1L, updatedProject);

        assertEquals("New Title", result.getTitle());
        assertEquals("New Description", result.getDescription());

        verify(projectRepository).save(existingProject);
    }

    @Test
    void deleteByIdShouldDeleteProject() {
        projectService.deleteById(1L);

        verify(projectRepository).deleteById(1L);
    }

    @Test
    void findByStatusShouldReturnMatchingProjects() {
        Project project1 = new Project();
        project1.setTitle("Logo Design");
        project1.setStatus(ProjectStatus.PLANNED);

        Project project2 = new Project();
        project2.setTitle("Album Cover");
        project2.setStatus(ProjectStatus.PLANNED);

        List<Project> plannedProjects = List.of(project1, project2);

        when(projectRepository.findByStatus(ProjectStatus.PLANNED))
                .thenReturn(plannedProjects);

        List<Project> result = projectService.findByStatus(ProjectStatus.PLANNED);

        assertEquals(2, result.size());
        assertEquals("Logo Design", result.get(0).getTitle());
        assertEquals("Album Cover", result.get(1).getTitle());
    }

    @Test
    void findByClientIdShouldReturnProjectsForClient(){
        Project project1 = new Project();
        project1.setTitle("Logo Design");

        Project project2 = new Project();
        project2.setTitle("Poster Design");

        List<Project> clientProjects = List.of(project1, project2);

        when(projectRepository.findByClientId(1L))
                .thenReturn(clientProjects);

        List<Project> result = projectService.findByClientId(1L);

        assertEquals(2, result.size());
        assertEquals("Logo Design", result.get(0).getTitle());
        assertEquals("Poster Design", result.get(1).getTitle());
    }

    @Test
    void markAsFinishedShouldSetStatusToFinished() {
        Project project = new Project();
        project.setStatus(ProjectStatus.IN_PROGRESS);

        when(projectRepository.findById(1L))
                .thenReturn(Optional.of(project));

        when(projectRepository.save(project))
                .thenReturn(project);

        Project result = projectService.markAsFinished(1L);

        assertEquals(ProjectStatus.FINISHED, result.getStatus());

        verify(projectRepository).save(project);
    }
}