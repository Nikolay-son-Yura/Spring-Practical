package ru.gb.Lesson8.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.gb.Lesson8.model.Roles;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {
    @Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }


    @Bean
    SecurityFilterChain noSecurity(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeHttpRequests(it -> it.anyRequest().permitAll()).build();
    }
//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(requests -> requests
//                        .requestMatchers("/home/timesheets/**").hasRole(Roles.USER.getName())
//                        .requestMatchers("/home/projects/**").hasRole(Roles.ADMIN.getName())
//                        .requestMatchers("/timesheets/**").hasRole(Roles.REST.getName())
//                        .requestMatchers("/projects/**").hasRole(Roles.REST.getName())
//                        .requestMatchers("/employees/**").hasRole(Roles.REST.getName())
//                )
//                .formLogin(Customizer.withDefaults())
//                .build();
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
