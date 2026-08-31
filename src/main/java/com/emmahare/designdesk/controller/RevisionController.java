package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.model.Project;
import com.emmahare.designdesk.model.Revision;
import com.emmahare.designdesk.service.ProjectService;
import com.emmahare.designdesk.service.RevisionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/project/{projectId")
    public String createRevision(
            @PathVariable Long projectId,
            @ModelAttribute Revision revision
    ) {
        Project project = projectService.findById(projectId);

        revision.setProject(project);

        revisionService.save(revision);

        return "redirect:/projects/" + projectId;
    }
}
