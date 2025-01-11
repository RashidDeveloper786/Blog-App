package com.project.blog_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.blog_app.entities.User;
import com.project.blog_app.exceptions.ResourceNotFoundException;
import com.project.blog_app.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("username", "with email", username));
        return user;
    }
    
}