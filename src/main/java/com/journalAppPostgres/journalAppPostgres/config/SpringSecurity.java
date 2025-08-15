//package net.engineeringdigest.journalApp.config;
package com.journalAppPostgres.journalAppPostgres.config;

import com.journalAppPostgres.journalAppPostgres.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // Replaces configureGlobal(...)
  @Bean
  public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService,
                                                       PasswordEncoder passwordEncoder) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder);
    return provider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                 AuthenticationProvider authenticationProvider) throws Exception {
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .authenticationProvider(authenticationProvider)
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/public/**", "/user/**").permitAll() // <-- user is public now
        .requestMatchers("/admin/**").hasRole("ADMIN")
        .requestMatchers("/journal/**").authenticated()
        .anyRequest().authenticated()
      )
      .httpBasic(Customizer.withDefaults())
      // .formLogin(Customizer.withDefaults())
      .build();
  }
}
