package com.airasia.usermanagement.payload.request;

import java.util.Set;

public class PermissionIdRequest {
    public Set<Integer> getPermissionIds() {
        return permissionIds;
    }

    public void setPermissionIds(Set<Integer> permissionIds) {
        this.permissionIds = permissionIds;
    }

    private Set<Integer> permissionIds;

}
