package com.farmchain.farmchain.dto;

public class AuthResponse {

    private String token ;
    private String role ;
    private String email;
    private long userId;

    public AuthResponse(String token, String role, String email, long userId) {
        this.token = token;
        this.role = role;
        this.email = email;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
