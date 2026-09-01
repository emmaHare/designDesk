package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.model.Client;
import com.emmahare.designdesk.service.ClientService;
import com.emmahare.designdesk.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
    @RequestMapping("/clients")
public class ClientController {

        private final ClientService clientService;
        private final ProjectService projectService;

        public ClientController(
                ClientService clientService,
                ProjectService projectService
        ) {
            this.clientService = clientService;
            this.projectService = projectService;
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
        public String createClient(
                @Valid @ModelAttribute Client client,
                BindingResult bindingResult
        ) {
            if (bindingResult.hasErrors()) {
                return "clients/new";
            }

            clientService.save(client);

            return "redirect:/clients";
        }

        @GetMapping("/{id}")
        public String showClient(@PathVariable Long id, Model model) {
            Client client = clientService.findById(id);

            model.addAttribute("client", client);
            model.addAttribute("projects", projectService.findByClientId(id));

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
                @Valid @ModelAttribute Client client,
                BindingResult bindingResult
        ) {
            if(bindingResult.hasErrors()) {
                return "clients/edit";
            }

            clientService.update(id, client);

            return "redirect:/clients/" + id;
        }

        @PostMapping("/{id}/delete")
        public String deleteClient(@PathVariable Long id) {
            clientService.deleteById(id);

            return "redirect:/clients";
        }
}
