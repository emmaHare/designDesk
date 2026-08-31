package com.emmahare.designdesk.repository;

import com.emmahare.designdesk.model.Revision;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RevisionRepository extends JpaRepository<Revision, Long> {

    List<Revision> findByProjectId(Long projectId);
}
