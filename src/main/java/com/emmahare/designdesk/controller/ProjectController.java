package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public String showAllProjects(Model model) {
        model.addAttribute("projects", projectService.findAll());
        return "projects/index";
    }
}
