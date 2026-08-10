package com.emmahare.designdesk.model;

import jakarta.persistence.*;

    @Entity
    @Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(unique = true, length = 255)
    private String email;

    @Column(name = "instagram_handle", length = 100)
    private String instagramHandle;

    @Column(columnDefinition = "TEXT")
        private String notes;

    public Client() {

    }
}
