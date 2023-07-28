package com.pragma.users.api.domain.model;

import java.time.LocalDate;

public class UserModel {

    private Long id;
    private String name;
    private String lastName;
    private String document;
    private String cellphone;
    private LocalDate birthdate;
    private String email;
    private String password;
    private Long idRole;

    public UserModel(Long id, String name, String lastName, String document, String cellphone, LocalDate birthdate, String email, String password, Long idRole) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.cellphone = cellphone;
        this.birthdate = birthdate;
        this.email = email;
        this.password = password;
        this.idRole = idRole;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDocument() {
        return document;
    }

    public String getCellphone() {
        return cellphone;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getIdRole() {
        return idRole;
    }
}
