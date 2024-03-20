package com.example.springbootkeycloak;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.Principal;

@SpringBootApplication
@RestController
public class SpringbootKeycloakApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootKeycloakApplication.class, args);
  }

  @PostMapping("/")
  public ResponseEntity hello() {
    return new ResponseEntity("Welcome to home page :)", HttpStatus.OK);
  }

}
