package com.emmahare.designdesk.service;

import com.emmahare.designdesk.model.Client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.emmahare.designdesk.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientService = new ClientService(clientRepository);
    }

    @Test
    void findAllShouldReturnAllClients() {
        Client client1 = new Client();
        client1.setName("Hazy Band");

        Client client2 = new Client();
        client2.setName("Lena Mayer");

        List<Client> clients = List.of(client1, client2);

        when(clientRepository.findAll()).thenReturn(clients);

        List<Client> result = clientService.findAll();

        assertEquals(2, result.size());
        assertEquals("Hazy Band", result.get(0).getName());
        assertEquals("Lena Mayer", result.get(1).getName());
    }

    @Test
    void findByIdShouldReturnClient() {
        Client client = new Client();
        client.setName("Hazy Band");

        when(clientRepository.findById(1L))
                .thenReturn(Optional.of(client));

        Client result = clientService.findById(1L);

        assertEquals("Hazy Band", result.getName());
    }

    @Test
    void findByIdShouldThrowExceptionWhenClientNonExistent() {
        when(clientRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> clientService.findById(99l)
        );
    }

    @Test
    void updateShouldUpdateExistingClient() {
        Client existingClient = new Client();
        existingClient.setName("Old Name");
        existingClient.setEmail("old@example.com");
        existingClient.setInstagramHandle("@old");
        existingClient.setNotes("Old notes");

        Client updatedClient = new Client();
        updatedClient.setName("New Name");
        updatedClient.setEmail("new@example.com");
        updatedClient.setInstagramHandle("@new");
        updatedClient.setNotes("New notes");

        when(clientRepository.findById(1L))
                .thenReturn(Optional.of(existingClient));

        when(clientRepository.save(existingClient))
                .thenReturn(existingClient);

        Client result = clientService.update(1L, updatedClient);

        assertEquals("New Name", result.getName());
        assertEquals("new@example.com", result.getEmail());
        assertEquals("@new", result.getInstagramHandle());
        assertEquals("New notes", result.getNotes());

        verify(clientRepository).save(existingClient);
    }

    @Test
    void saveShouldReturnSavedClient() {
        Client client = new Client();
        client.setName("New Client");

        when(clientRepository.save(client))
                .thenReturn(client);

        Client result = clientService.save(client);

        assertEquals("New Client", result.getName());
    }

    @Test
    void deleteByIdShouldDeleteClient() {
        clientService.deleteById(1L);

        verify(clientRepository).deleteById(1L);
    }
}
