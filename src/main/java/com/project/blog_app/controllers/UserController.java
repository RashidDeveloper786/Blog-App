package com.project.blog_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog_app.payloads.ApiResopnse;
import com.project.blog_app.payloads.UserDto;
import com.project.blog_app.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
@EnableMethodSecurity(prePostEnabled = true)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    // Get All users/....
    
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    // Get Single user by User Id....
    
    @GetMapping("/{userid}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Integer userid) {
        return ResponseEntity.ok(this.userService.getUserById(userid));
    }

    // Update user by user_id
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
            @PathVariable("userId") Integer uid) {
        UserDto userDto2 = this.userService.updateUser(userDto, uid);
        return new ResponseEntity<>(userDto2, HttpStatus.OK);
    }

    // Delete user by user id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResopnse> deleteUser(@PathVariable Integer userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<ApiResopnse>(new ApiResopnse("User Deleted Successfully...", true), HttpStatus.OK);
    }
}
