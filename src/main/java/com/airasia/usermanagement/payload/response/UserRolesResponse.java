package com.airasia.usermanagement.payload.response;

import java.util.Set;

public class UserRolesResponse {
    private String message;

    public UserRolesResponse(String message, Set<String> unassignedRoles) {
        this.message = message;
        this.unassignedRoles = unassignedRoles;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getUnassignedRoles() {
        return unassignedRoles;
    }

    public void setUnassignedRoles(Set<String> unassignedRoles) {
        this.unassignedRoles = unassignedRoles;
    }

    private Set<String> unassignedRoles;
}
