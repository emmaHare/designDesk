package com.emmahare.designdesk.controller;

import com.emmahare.designdesk.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
