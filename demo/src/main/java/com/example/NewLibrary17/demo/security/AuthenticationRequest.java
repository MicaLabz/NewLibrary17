package com.example.NewLibrary17.demo.security;

public class AuthenticationRequest {
    private String dni;
    private String password;

    public AuthenticationRequest(String dni, String password) {
        this.dni = dni;
        this.password = password;
    }

    public AuthenticationRequest() {
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
