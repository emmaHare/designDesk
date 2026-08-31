package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.model.Client;
import com.emmahare.designdesk.model.Project;
import com.emmahare.designdesk.model.ProjectStatus;
import com.emmahare.designdesk.service.ClientService;
import com.emmahare.designdesk.service.ProjectService;
import com.emmahare.designdesk.service.RevisionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @Mock
    private ClientService clientService;

    @Mock
    private RevisionService revisionService;

    private ProjectController projectController;

    @BeforeEach
    void setUp() {
        projectController = new ProjectController(projectService, clientService, revisionService);
    }

    @Test
    void showAllProjectsShouldReturnProjectsIndex() {
        Model model = mock(Model.class);

        Project project1 = new Project();
        project1.setTitle("Logo Design");

        Project project2 = new Project();
        project2.setTitle("Album Cover");

        List<Project> projects = List.of(project1, project2);

        when(projectService.findAll()).thenReturn(projects);

        String viewName = projectController.showAllProjects(null, model);

        assertEquals("projects/index", viewName);

        verify(projectService).findAll();
        verify(model).addAttribute("projects", projects);
        verify(model).addAttribute("selectedStatus", null);
    }

    @Test
    void showAllProjectsShouldFilterByStatus() {
        Model model = mock(Model.class);

        Project project = new Project();
        project.setTitle("Logo Design");
        project.setStatus(ProjectStatus.PLANNED);

        List<Project> projects = List.of(project);

        when(projectService.findByStatus(ProjectStatus.PLANNED))
                .thenReturn(projects);

        String viewName = projectController.showAllProjects(
                ProjectStatus.PLANNED,
                model
        );

        assertEquals("projects/index", viewName);

        verify(projectService).findByStatus(ProjectStatus.PLANNED);
        verify(model).addAttribute("projects", projects);
        verify(model).addAttribute("selectedStatus", ProjectStatus.PLANNED);
    }

    @Test
    void showProjectShouldReturnProjectDetails() {
        Model model = mock(Model.class);

        Project project = new Project();
        project.setTitle("Logo Design");

        when(projectService.findById(1L))
                .thenReturn(project);

        String viewName = projectController.showProject(1L, model);

        assertEquals("projects/details", viewName);

        verify(projectService).findById(1L);
        verify(model).addAttribute("project", project);
    }

    @Test
    void showCreateFormShouldReturnNewProjectPage() {
        Model model = mock(Model.class);

        Client client = new Client();
        client.setName("Hazy Band");

        List<Client> clients = List.of(client);

        when(clientService.findAll()).thenReturn(clients);

        String viewName = projectController.showCreateForm(model);

        assertEquals("projects/new", viewName);

        verify(model).addAttribute(eq("project"), any(Project.class));
        verify(model).addAttribute("clients", clients);
    }

    @Test
    void createProjectShouldSaveProjectAndRedirect() {
        Project project = new Project();
        project.setTitle("New Project");

        String viewName = projectController.createProject(project);

        verify(projectService).save(project);
        assertEquals("redirect:/projects", viewName);
    }

    @Test
    void showEditFormShouldReturnEditPage() {
        Model model = mock(Model.class);

        Project project = new Project();
        project.setTitle("Logo Design");

        Client client = new Client();
        client.setName("Hazy Band");

        List<Client> clients = List.of(client);

        when(projectService.findById(1L)).thenReturn(project);
        when(clientService.findAll()).thenReturn(clients);

        String viewName = projectController.showEditForm(1L, model);

        assertEquals("projects/edit", viewName);

        verify(projectService).findById(1L);
        verify(clientService).findAll();
        verify(model).addAttribute("project", project);
        verify(model).addAttribute("clients", clients);
    }

    @Test
    void updateProjectShouldUpdateAndRedirect() {
        Project updatedProject = new Project();
        updatedProject.setTitle("Updated Project");

        String viewName = projectController.updateProject(1L, updatedProject);

        verify(projectService).update(1L, updatedProject);
        assertEquals("redirect:/projects/1", viewName);
    }

    @Test
    void deleteProjectShouldDeleteAndRedirect() {
        String viewName = projectController.deleteProject(1L);

        verify(projectService).deleteById(1L);
        assertEquals("redirect:/projects", viewName);
    }

    @Test
    void markProjectAsFinishedShouldFinishAndRedirect() {
        String viewName = projectController.markProjectAsFinished(1L);

        verify(projectService).markAsFinished(1L);
        assertEquals("redirect:/projects/1", viewName);
    }
}
