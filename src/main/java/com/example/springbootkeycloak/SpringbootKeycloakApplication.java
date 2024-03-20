package com.example.springbootkeycloak;

import com.example.springbootkeycloak.authenticator.AuthResponse;
import com.example.springbootkeycloak.authenticator.KeyCloakAuthenticator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;


@SpringBootApplication
@RestController
public class SpringbootKeycloakApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringbootKeycloakApplication.class, args);
  }

  @GetMapping("/")
  public ResponseEntity hello() {
    return new ResponseEntity("Welcome to home page :)", HttpStatus.OK);
  }

  @PostMapping("/login")
  public void login(HttpServletRequest request, HttpServletResponse response){
    try {
      String token = authenticate(response,request.getParameter("username"),request.getParameter("password"));
      response.sendRedirect("/?access_token="+token);
    }
    catch (Exception e){
      System.err.println(e);
    }
  }

  public String authenticate(HttpServletResponse response,String username,String password) throws URISyntaxException {

    KeyCloakAuthenticator authenticator = KeyCloakAuthenticator.builder()
      .tokenURL("http://localhost:8080/realms/SpringSSO/protocol/openid-connect/token")
      .clientID("spring-keycloak")
      .clientSecret("UVpm5yTahJ4dEcjt0uvdMcP5voQuLfuR")
      .grantType("password")
      .username(username)
      .password(password)
      .build();

    ResponseEntity<AuthResponse> authResponse = authenticator.authenticate();

    if(authResponse.getStatusCode() != HttpStatus.OK)
      throw new BadCredentialsException("Bad Credentials.......");
    return authResponse.getBody().access_token;
  }

}
