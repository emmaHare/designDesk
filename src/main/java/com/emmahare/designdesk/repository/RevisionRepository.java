package com.emmahare.designdesk.repository;

import com.emmahare.designdesk.model.Revision;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RevisionRepository extends JpaRepository<Revision, Long> {
}
