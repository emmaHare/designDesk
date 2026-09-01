package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.model.Project;
import com.emmahare.designdesk.model.Revision;
import com.emmahare.designdesk.service.ProjectService;
import com.emmahare.designdesk.service.RevisionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/revisions")
public class RevisionController {

    private final RevisionService revisionService;
    private final ProjectService projectService;

    public RevisionController(
            RevisionService revisionService,
            ProjectService projectService
    ) {
        this.revisionService = revisionService;
        this.projectService = projectService;
    }

    @PostMapping("/project/{projectId}")
    public String createRevision(
            @PathVariable Long projectId,
            @ModelAttribute Revision revision
    ) {
        Project project = projectService.findById(projectId);

        //Project, date and completion status are set automatically when a revision is created.
        revision.setProject(project);
        revision.setDate(LocalDate.now());
        revision.setCompleted(false);

        revisionService.save(revision);

        return "redirect:/projects/" + projectId;
    }

    @GetMapping("/project/{projectId}/new")
    public String showCreateForm(
            @PathVariable Long projectId,
            Model model
    ) {
        Project project = projectService.findById(projectId);

        model.addAttribute("project", project);
        model.addAttribute("revision", new Revision());

        return "revisions/new";
    }

    @PostMapping("/{id}/complete")
    public String markRevisionAsCompleted(
            @PathVariable Long id,
            @RequestParam Long projectId
    ) {
        revisionService.markAsCompleted(id);

        return "redirect:/projects/" + projectId;
    }
}
