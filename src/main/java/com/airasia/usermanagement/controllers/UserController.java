package com.airasia.usermanagement.controllers;


import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;

import com.airasia.usermanagement.models.Permission;
import com.airasia.usermanagement.models.Role;
import com.airasia.usermanagement.models.User;
import com.airasia.usermanagement.payload.request.PermissionIdRequest;
import com.airasia.usermanagement.payload.request.RolesRequest;
import com.airasia.usermanagement.payload.response.MessageResponse;
import com.airasia.usermanagement.payload.response.UserPermissionsResponse;
import com.airasia.usermanagement.payload.response.UserRolesResponse;
import com.airasia.usermanagement.repository.PermissionRepository;
import com.airasia.usermanagement.repository.RoleRepository;
import com.airasia.usermanagement.repository.UserRepository;
import com.airasia.usermanagement.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {
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

    @GetMapping("{id}/roles")
    public ResponseEntity<?> getAvailableRoles(@Valid @PathVariable("id") Long id) {
              User user =userRepository.findOne(id);
        if (user==null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User does not exist!"));
        }
        Set<Role> roles=user.getRoles();
            return ResponseEntity.ok(roles);
    }

    @PostMapping("{id}/roles")
    public ResponseEntity<?> addRolesToUser(@Valid @RequestBody RolesRequest rolesRequest, @PathVariable("id") long id) {


        // Attach user to roles
        User user = userRepository.findOne(id);
        if (user==null) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User does not exist!"));
        }
        Set<Role> attachingRoles = new HashSet<>();
        Set<String> notFoundRoles = new HashSet<>();
        if (rolesRequest == null) {
            new RuntimeException("Error: Roles not provided.");
        } else {
            rolesRequest.getRoles().forEach(role -> {
                Optional<Role> rolesFound = roleRepository.findByName(role);
                if(rolesFound.isPresent()) {
                    attachingRoles.add(rolesFound.get());
                }
                else{
                    notFoundRoles.add(role);
                } });
        }
        if(attachingRoles.isEmpty())
        {
            return ResponseEntity.ok(new MessageResponse("Error: Roles not attached to user due to all invalid roles"));
        }
        else {
            user.setRoles(attachingRoles);
            userRepository.save(user);
            return ResponseEntity.ok(new UserRolesResponse("Role Created Successfully", notFoundRoles));
        }
    }


    @GetMapping("{id}/permissions")
    public ResponseEntity<?> getAvailablePermissionsToUser(@Valid @RequestBody PermissionIdRequest permissionIdRequest, @PathVariable("id") Long id) {
        User user=userRepository.findOne(id);
        if(user==null)
        {
            return ResponseEntity.ok(new MessageResponse("User Does not exist"));
        }
        Set<Permission> permissions=new HashSet<>();
        Set<Role> roles=user.getRoles();
        roles.forEach(role ->{
            permissions.addAll(role.getPermissions());
        });
      if(permissions.isEmpty())
      {
          return ResponseEntity.ok(new MessageResponse("User Does not have any permissions"));
      }
      AtomicInteger flag= new AtomicInteger();
      List<Integer> notAllowedPermissions=new ArrayList<Integer>();
        List<Integer> allowedPermissions=new ArrayList<Integer>();
        permissionIdRequest.getPermissionIds().forEach(permissionId -> {
            flag.set(0);
            permissions.forEach(permission -> {
                if (permission.getId() == permissionId) {
                    allowedPermissions.add(permissionId);
                    flag.set(1);
                }
            });
            if(flag.get()==0)
         notAllowedPermissions.add(permissionId);
        });
      return ResponseEntity.ok(new UserPermissionsResponse(notAllowedPermissions,allowedPermissions));
    }

}