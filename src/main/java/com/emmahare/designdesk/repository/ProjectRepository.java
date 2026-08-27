package com.emmahare.designdesk.repository;

import com.emmahare.designdesk.model.Project;
import com.emmahare.designdesk.model.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByStatus(ProjectStatus status);
}
