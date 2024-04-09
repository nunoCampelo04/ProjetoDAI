package com.example.myapplication;


public class User {
    private String email;
    private String password;
    private String nome;

    public User(String username, String password, String nome) {
        this.email = username;
        this.password = password;
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }
}

