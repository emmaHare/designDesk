package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.model.Client;
import com.emmahare.designdesk.service.ClientService;
import com.emmahare.designdesk.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @Mock
    private ProjectService projectService;

    private ClientController clientController;

    @BeforeEach
    void setUp() {
        clientController = new ClientController(clientService, projectService);
    }

    @Test
    void showAllClientsShouldReturnClientsIndex() {
        Model model = mock(Model.class);

        Client client1 = new Client();
        client1.setName("Hazy Band");

        Client client2 = new Client();
        client2.setName("Lena Mayer");

        List<Client> clients = List.of(client1, client2);

        when(clientService.findAll()).thenReturn(clients);

        String viewName = clientController.showAllClients(model);

        assertEquals("clients/index", viewName);

        verify(clientService).findAll();
        verify(model).addAttribute("clients", clients);
    }

    @Test
    void showClientShouldReturnClientDetails() {
        Model model = mock(Model.class);

        Client client = new Client();
        client.setName("Hazy Band");

        when(clientService.findById(1L)).thenReturn(client);

        String viewName = clientController.showClient(1L, model);

        assertEquals("clients/details", viewName);

        verify(clientService).findById(1L);
        verify(model).addAttribute("client", client);
    }

    @Test
    void showCreateFormShouldReturnNewClientPage() {
        Model model = mock(Model.class);

        String viewName = clientController.showCreateForm(model);

        assertEquals("clients/new", viewName);

        verify(model).addAttribute(eq("client"), any(Client.class));
    }

    @Test
    void createClientShouldSaveClientAndRedirect() {
        Client client = new Client();
        client.setName("New Client");

        String viewName = clientController.createClient(client);

        verify(clientService).save(client);
        assertEquals("redirect:/clients", viewName);
    }

    @Test
    void showEditFormShouldReturnEditPage() {
        Model model = mock(Model.class);

        Client client = new Client();
        client.setName("Hazy Band");

        when(clientService.findById(1L)).thenReturn(client);

        String viewName = clientController.showEditForm(1L, model);

        assertEquals("clients/edit", viewName);

        verify(clientService).findById(1L);
        verify(model).addAttribute("client", client);
    }

    @Test
    void updateClientShouldSaveChangesAndRedirect() {
        Client editedClient = new Client();
        editedClient.setName("New Name");
        editedClient.setEmail("new@example.com");
        editedClient.setInstagramHandle("@new");
        editedClient.setNotes("New notes");

        String viewName = clientController.updateClient(1L, editedClient);

        verify(clientService).update(1L, editedClient);

        assertEquals("redirect:/clients/1", viewName);
    }

    @Test
    void deleteClientShouldDeleteAndRedirect() {
        String viewName = clientController.deleteClient(1L);

        verify(clientService).deleteById(1L);
        assertEquals("redirect:/clients", viewName);
    }
}
