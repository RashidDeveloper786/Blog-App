package com.project.blog_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.project.blog_app.security.CustomUserDetailService;
import com.project.blog_app.security.JwtAuthenticationEntryPoint;
import com.project.blog_app.security.JwtAuthenticationFilter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity

public class SecurityConfig {
    
    @Autowired
    private  CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    public static final String[] PUBLIC_URLS = {
        "/api/v1/auth/**",
        "/v3/api-docs/**",
        "/swagger-ui.html",
        "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf->csrf.disable())
            .authorizeHttpRequests(request->request
            .requestMatchers(PUBLIC_URLS)
            .permitAll()
            .requestMatchers(HttpMethod.GET)
            .permitAll()
            .anyRequest()
            .authenticated()
            )
            .exceptionHandling(ex->ex.authenticationEntryPoint(this.jwtAuthenticationEntryPoint))
            .sessionManagement(sc->sc
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

            http.authenticationProvider(daoAuthenticationProvider());

            http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
 
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;

    }

     @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
