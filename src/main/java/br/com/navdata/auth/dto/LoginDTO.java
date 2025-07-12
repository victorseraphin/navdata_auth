package br.com.navdata.auth.dto;

public class LoginDTO {
    private String email;
    private String password;

    // Getters e setters
    public String getEmail() { return email; }
    public void setEmail(String username) { this.email = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}