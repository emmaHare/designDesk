package com.emmahare.designdesk.service;

import com.emmahare.designdesk.exception.RevisionNotFoundException;
import com.emmahare.designdesk.model.Revision;
import com.emmahare.designdesk.repository.RevisionRepository;
import org.hibernate.validator.constraints.Mod10Check;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RevisionServiceTest {

    @Mock
    private RevisionRepository revisionRepository;

    private RevisionService revisionService;

    @BeforeEach
    void setUp() {
        revisionService = new RevisionService(revisionRepository);
    }

    @Test
    void findAllShouldReturnAllRevisions() {
        Revision revision1 = new Revision();
        revision1.setFeedback("Change the font");

        Revision revision2 = new Revision();
        revision2.setFeedback("Make the colors darker");

        List<Revision> revisions = List.of(revision1, revision2);

        when(revisionRepository.findAll()).thenReturn(revisions);

        List<Revision> result = revisionService.findAll();

        assertEquals(2, result.size());
        assertEquals("Change the font", result.get(0).getFeedback());
        assertEquals("Make the colors darker", result.get(1).getFeedback());
    }

    @Test
    void findByProjectShouldReturnRevisionsForProject() {
        Revision revision1 = new Revision();
        revision1.setFeedback("Change the font");

        Revision revision2 = new Revision();
        revision2.setFeedback("Make the colors darker");

        List<Revision> revisions = List.of(revision1, revision2);

        when(revisionRepository.findByProjectId(1L))
                .thenReturn(revisions);

        List<Revision> result = revisionService.findByProjectId(1L);

        assertEquals(2, result.size());
        assertEquals("Change the font", result.get(0).getFeedback());
        assertEquals("Make the colors darker", result.get(1).getFeedback());
    }

    @Test
    void saveShouldReturnSavedRevision() {
        Revision revision = new Revision();
        revision.setFeedback("Change the font");

        when(revisionRepository.save(revision))
                .thenReturn(revision);

        Revision result = revisionService.save(revision);

        assertEquals("Change the font", result.getFeedback());
    }

    @Test
    void deleteByIdShouldDeleteRevision() {
        revisionService.deleteById(1L);

        verify(revisionRepository).deleteById(1L);
    }

    @Test
    void maskAsCompletedShouldSetCompletedToTrue() {
        Revision revision = new Revision();
        revision.setCompleted(false);

        when(revisionRepository.findById(1L))
                .thenReturn(Optional.of(revision));

        when(revisionRepository.save(revision))
                .thenReturn(revision);

        Revision result = revisionService.markAsCompleted(1L);

        assertEquals(true, result.isCompleted());
        verify(revisionRepository).save(revision);
    }

    @Test
    void markAsCompletedShouldThrowExceotionWhenRevisionNonExistent() {
        when(revisionRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                RevisionNotFoundException.class,
                () -> revisionService.markAsCompleted(1L)
        );
    }
}
