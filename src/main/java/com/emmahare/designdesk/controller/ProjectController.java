package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.model.Project;
import com.emmahare.designdesk.service.ClientService;
import com.emmahare.designdesk.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final ClientService clientService;

    public ProjectController(ProjectService projectService, ClientService clientService) {
        this.projectService = projectService;
        this.clientService = clientService;
    }

    @GetMapping
    public String showAllProjects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "projects/index";
    }

    @GetMapping("/{id}")
    public String showProject(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);

        model.addAttribute("project", project);

        return "projects/details";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("clients", clientService.findAll());

        return "projects/new";
    }

    @PostMapping
    public String createProject(@ModelAttribute Project project) {
        projectService.save(project);

        return "redirect:/projects";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Project project = projectService.findById(id);

        model.addAttribute("project", project);
        model.addAttribute("clients", clientService.findAll());

        return "projects/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateProject(
            @PathVariable Long id,
            @ModelAttribute Project project
    ) {
        projectService.update(id, project);

        return "redirect:/projects/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteProject(@PathVariable Long id) {
        projectService.deleteById(id);

        return "redirect:/projects";
    }
}
