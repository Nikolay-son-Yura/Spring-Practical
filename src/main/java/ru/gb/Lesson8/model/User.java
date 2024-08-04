package ru.gb.Lesson8.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "login")
    private String login;


    @Column(name = "password")
    private String password;

    @Column(name = "roleId")
    private Long roleId;

}

