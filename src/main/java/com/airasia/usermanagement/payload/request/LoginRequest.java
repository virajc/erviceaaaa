package com.airasia.usermanagement.payload.request;


import org.hibernate.validator.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String usernameoremail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail() {
        return usernameoremail;
    }

    public void setUsernameOrEmail(String username) {
        this.usernameoremail = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
