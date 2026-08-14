package com.emmahare.designdesk.service;

import com.emmahare.designdesk.model.Client;
import com.emmahare.designdesk.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Client with ID" + id + " was not found."
                        )
                );
    }
}
