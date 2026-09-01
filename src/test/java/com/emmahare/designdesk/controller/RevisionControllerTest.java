package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.model.Project;
import com.emmahare.designdesk.model.Revision;
import com.emmahare.designdesk.service.ProjectService;
import com.emmahare.designdesk.service.RevisionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RevisionControllerTest {

    @Mock
    private RevisionService revisionService;

    @Mock
    private ProjectService projectService;

    private RevisionController revisionController;

    @BeforeEach
    void setUp() {
        revisionController = new RevisionController(
                revisionService,
                projectService
        );
    }

    @Test
    void showCreateFormShouldReturnNewRevisionPage() {
        Project project = new Project();
        Model model = mock(Model.class);

        when(projectService.findById(1L))
                .thenReturn(project);

        String result = revisionController.showCreateForm(1L, model);

        assertEquals("revisions/new", result);

        verify(projectService).findById(1L);
        verify(model).addAttribute("project", project);
        verify(model).addAttribute(eq("revision"), any());
    }

    @Test
    void createRevisionShouldSaveRevisionAndRedirect() {
        Project project = new Project();
        Revision revision = new Revision();

        when(projectService.findById(1L))
                .thenReturn(project);

        String result = revisionController.createRevision(1L, revision);

        assertEquals("redirect:/projects/1", result);

        assertEquals(project, revision.getProject());
        assertEquals(LocalDate.now(), revision.getDate());
        assertEquals(false, revision.isCompleted());

        verify(revisionService).save(revision);
    }

    @Test
    void markRevisionAsCompletedShouldCompleteAndRedirect() {
        String result = revisionController.markRevisionAsCompleted(1L, 2L);

        assertEquals("redirect:/projects/2", result);

        verify(revisionService).markAsCompleted(1L);
    }
}
