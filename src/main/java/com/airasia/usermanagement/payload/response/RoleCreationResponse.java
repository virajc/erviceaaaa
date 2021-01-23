package com.airasia.usermanagement.payload.response;

import java.util.Set;

public class RoleCreationResponse {

    public RoleCreationResponse(String message,Set<String> unassignedPermissions)
    {
       this.message=message;
       this.unassignedPermissions=unassignedPermissions;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getUnassignedPermissions() {
        return unassignedPermissions;
    }

    public void setUnassignedPermissions(Set<String> unassignedPermissions) {
        this.unassignedPermissions = unassignedPermissions;
    }

    private String message;
    private Set<String> unassignedPermissions;
}
