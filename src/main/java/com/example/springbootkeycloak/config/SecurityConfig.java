package com.example.springbootkeycloak.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private JWTAuthConverter jwtAuthConverter;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

    httpSecurity
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(t -> {
        t
          .requestMatchers("/").hasRole("default-roles-spring")
          .anyRequest().authenticated();
      })
      .oauth2ResourceServer(t -> {
        t.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthConverter));
      })
      .sessionManagement(
        t -> t.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      );


    return httpSecurity.build();
  }

}
