package com.airasia.usermanagement.payload.request;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Set;

import javax.validation.constraints.*;

public class PermissionRequest {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotBlank
    @Size(max = 50)
    private String name;



}