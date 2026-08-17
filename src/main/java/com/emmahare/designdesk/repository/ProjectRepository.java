package com.emmahare.designdesk.repository;

import com.emmahare.designdesk.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
