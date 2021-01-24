package com.airasia.usermanagement.payload.request;

import java.util.Set;

public class RolesRequest {

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    private Set<String> roles;
}
