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
import org.springframework.validation.BindingResult;

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
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = clientController.createClient(client, bindingResult);

        assertEquals("redirect:/clients", viewName);

        verify(clientService).save(client);
    }

    @Test
    void createClientShouldReturnNewPageWhenValidationFails() {
        Client client = new Client();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = clientController.createClient(client, bindingResult);

        assertEquals("clients/new", viewName);

        verify(clientService, never()).save(client);
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
        Client client = new Client();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        String viewName = clientController.updateClient(1L, client, bindingResult);

        assertEquals("redirect:/clients/1", viewName);

        verify(clientService).update(1L, client);
    }

    @Test
    void updateClientShouldReturnEditPageWhenValidationFails() {
        Client client = new Client();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = clientController.updateClient(1L, client, bindingResult);

        assertEquals("clients/edit", viewName);

        verify(clientService, never()).update(1L, client);
    }

    @Test
    void deleteClientShouldDeleteAndRedirect() {
        String viewName = clientController.deleteClient(1L);

        verify(clientService).deleteById(1L);
        assertEquals("redirect:/clients", viewName);
    }
}
