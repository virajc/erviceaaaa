package com.airasia.usermanagement.controllers;


import com.airasia.usermanagement.models.Permission;
import com.airasia.usermanagement.payload.request.PermissionRequest;
import com.airasia.usermanagement.payload.response.MessageResponse;
import com.airasia.usermanagement.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PermissionRepository permissionRepository;


    @GetMapping
    public ResponseEntity<?> getAvailablePermissions() {

        List<Permission> permissions = permissionRepository.findAll();

        return ResponseEntity.ok(permissions);
    }

    @PostMapping
    public ResponseEntity<?> createPermission(@Valid @RequestBody PermissionRequest permissionRequest) {
        if (permissionRepository.findByName(permissionRequest.getName()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Permission is already created!"));
        }

        // Create new permission
        Permission user = new Permission(permissionRequest.getName());
        permissionRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Permission successfully!"));
    }
}