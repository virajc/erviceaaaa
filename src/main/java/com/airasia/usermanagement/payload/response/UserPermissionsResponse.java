package com.airasia.usermanagement.payload.response;

import java.util.List;
import java.util.Set;

public class UserPermissionsResponse {
      private List<Integer> notAllowedPermissions;

    public UserPermissionsResponse(List<Integer> notAllowedPermissions, List<Integer> allowedPermissions) {
        this.notAllowedPermissions = notAllowedPermissions;
        this.allowedPermissions = allowedPermissions;
    }

    public List<Integer> getNotAllowedPermissions() {
        return notAllowedPermissions;
    }

    public void setNotAllowedPermissions(List<Integer> notAllowedPermissions) {
        this.notAllowedPermissions = notAllowedPermissions;
    }

    public List<Integer> getAllowedPermissions() {
        return allowedPermissions;
    }

    public void setAllowedPermissions(List<Integer> allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
    }

    private List<Integer> allowedPermissions;


}
