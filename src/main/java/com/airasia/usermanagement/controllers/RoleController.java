package com.airasia.usermanagement.controllers;


import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.airasia.usermanagement.models.Permission;
import com.airasia.usermanagement.models.Role;
import com.airasia.usermanagement.models.User;
import com.airasia.usermanagement.payload.request.LoginRequest;
import com.airasia.usermanagement.payload.request.RoleWithPermissionRequest;
import com.airasia.usermanagement.payload.request.SignupRequest;
import com.airasia.usermanagement.payload.response.JwtResponse;
import com.airasia.usermanagement.payload.response.MessageResponse;
import com.airasia.usermanagement.payload.response.RoleCreationResponse;
import com.airasia.usermanagement.repository.PermissionRepository;
import com.airasia.usermanagement.repository.RoleRepository;
import com.airasia.usermanagement.repository.UserRepository;
import com.airasia.usermanagement.security.jwt.JwtUtils;
import com.airasia.usermanagement.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @GetMapping
    public ResponseEntity<?> getAvailableRoles() {

        List<Role> roles = roleRepository.findAll();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<?> createRolesWithPermission(@Valid @RequestBody RoleWithPermissionRequest roleWithPermissionRequest) {
        if (roleRepository.findByName(roleWithPermissionRequest.getRole()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Role is already created!"));
        }



        // Create new role
        Role role = new Role(roleWithPermissionRequest.getRole());

        Set<String> strPermissions = roleWithPermissionRequest.getPermission();
        Set<Permission> permissions = new HashSet<>();
        Set<String> notFoundPermissions = new HashSet<>();
        if (strPermissions == null) {
             new RuntimeException("Error: Permission not provided.");
        } else {
            strPermissions.forEach(permission -> {
                        Optional<Permission> permissionFound = permissionRepository.findByName(permission);
                        if(permissionFound.isPresent()) {
                            permissions.add(permissionFound.get());
                        }
                        else{
                            notFoundPermissions.add(permission);
                        } });
        }
         if(permissions.isEmpty())
         {
             return ResponseEntity.ok(new MessageResponse("Error: Role not created due to all invalid permissions"));
         }
         else {
             role.setPermissions(permissions);
             roleRepository.save(role);
             return ResponseEntity.ok(new RoleCreationResponse("Role Created Successfully", notFoundPermissions));
         }
    }
}