package com.project.blog_app.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog_app.entities.User;
import com.project.blog_app.exceptions.ApiException;
import com.project.blog_app.payloads.JwtAuthRequest;
import com.project.blog_app.payloads.JwtAuthResponse;
import com.project.blog_app.payloads.UserDto;
import com.project.blog_app.security.JwtTokenHelper;
import com.project.blog_app.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;

    // @Autowired
	// private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
        
        this.authenticate(request.getUsername(), request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

        String token = this.jwtTokenHelper.generateToken(userDetails);
        
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(token);
        response.setUser(this.modelMapper.map((User) userDetails, UserDto.class));
        return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);

    }

    public void authenticate(String username, String password) throws Exception{
        
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
        
        try {
            this.authenticationManager.authenticate(authToken);
        } catch (BadCredentialsException e) {
            System.out.println("Invalid Credentials");
            throw new ApiException("Invalid Username or Password Exception");
        }
    }


    //New User Registration

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto registeredUserDto = this.userService.registerNewUser(userDto);
        
        return new ResponseEntity<UserDto>(registeredUserDto, HttpStatus.CREATED);
        
    }
    
}
