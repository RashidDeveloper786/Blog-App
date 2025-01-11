package com.project.blog_app.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.blog_app.exceptions.*;
import com.project.blog_app.config.AppConstants;
import com.project.blog_app.entities.Role;
import com.project.blog_app.entities.User;
import com.project.blog_app.payloads.UserDto;
import com.project.blog_app.repositories.RoleRepo;
import com.project.blog_app.repositories.UserRepo;
import com.project.blog_app.services.UserService;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        
        User user = this.dtoToUser(userDto);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.userToDto(updatedUser);
        return userDto1;
    }

    @Override
    public UserDto getUserById(Integer userId) {

        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    //Converting User to Dto

    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    //Coverting Dto to User
    private UserDto userToDto(User user) {

        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);

        //encoding the password
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));

        //assignign the role
        Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);

        User newUser = this.userRepo.save(user);

        UserDto newUserDto = this.modelMapper.map(newUser, UserDto.class);
        return newUserDto;

    }

}