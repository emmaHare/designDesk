package com.emmahare.designdesk.service;

import com.emmahare.designdesk.model.Revision;
import com.emmahare.designdesk.repository.RevisionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RevisionService {

    private final RevisionRepository revisionRepository;

    public RevisionService(RevisionRepository revisionRepository) {
        this.revisionRepository = revisionRepository;
    }

    public List<Revision> findAll() {
        return revisionRepository.findAll();
    }

    public Revision save(Revision revision) {
        return revisionRepository.save(revision);
    }

    public void deleteById(Long id) {
        revisionRepository.deleteById(id);
    }

    public List<Revision> findByProjectId(Long projectId) {
        return revisionRepository.findByProjectId(projectId);
    }

    public Revision markAsCompleted(Long id) {
        Revision revision = revisionRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Revision with ID " + id + " was not found."
                        ));

        revision.setCompleted(true);

        return revisionRepository.save(revision);
    }
}
