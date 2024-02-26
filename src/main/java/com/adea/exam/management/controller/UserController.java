package com.adea.exam.management.controller;

import com.adea.exam.management.controller.beans.*;
import com.adea.exam.management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/{id}")
    public UserRecordResponse getUser(@PathVariable String id) {
        return userService.getUser(id);
    }

    @PostMapping
    public Response addUser(@RequestBody UserRecord userRecord) {
        return userService.setNewUser(userRecord);
    }

    @PostMapping("/list")
    public UsersResponse userResponse(@RequestBody UserFilterRequest userFilterRequest) {
        return userService.getUsers(userFilterRequest);
    }

    @PutMapping
    public Response updateUser(@RequestBody UserRecord userRecord) {
        return userService.updateUser(userRecord);
    }

    @DeleteMapping("/{idUser}")
    public Response revoke(@PathVariable String idUser) {
        return userService.revokeUser(idUser);
    }
}
