package com.example.myapplication;

public class User {
    private String username;
    private String password;
    private String nome;

    public User(String username, String password, String nome) {
        this.username = username;
        this.password = password;
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNome() {
        return nome;
    }
}

