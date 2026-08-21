package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.model.Client;
import com.emmahare.designdesk.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
    @RequestMapping("/clients")
public class ClientController {

        private final ClientService clientService;

        public ClientController(ClientService clientService) {
            this.clientService = clientService;
        }

        @GetMapping
        public String showAllClients(Model model) {
            model.addAttribute("clients", clientService.findAll());

            return "clients/index";
        }

        @GetMapping("/new")
        public String showCreateForm(Model model) {
            model.addAttribute("client", new Client());
            return "clients/new";
        }

        @PostMapping
        public String createClient(@ModelAttribute Client client) {
            clientService.save(client);
            return "redirect:/clients";
        }

        @GetMapping("/{id}")
        public String showClient(@PathVariable Long id, Model model) {
            Client client = clientService.findById(id);

            model.addAttribute("client", client);

            return "clients/details";
        }

        @GetMapping("/{id}/edit")
        public String showEditForm(@PathVariable Long id, Model model) {
            Client client = clientService.findById(id);

            model.addAttribute("client", client);

            return "clients/edit";
        }

        @PostMapping("/{id}/edit")
        public String updateClient(
                @PathVariable Long id,
                @ModelAttribute Client client
        ) {
            clientService.update(id, client);

            return "redirect:/clients/" + id;
        }

        @PostMapping("/{id}/delete")
        public String deleteClient(@PathVariable Long id) {
            clientService.deleteById(id);

            return "redirect:/clients";
        }
}
