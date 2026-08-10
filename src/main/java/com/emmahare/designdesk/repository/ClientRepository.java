package com.emmahare.designdesk.repository;

import com.emmahare.designdesk.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
