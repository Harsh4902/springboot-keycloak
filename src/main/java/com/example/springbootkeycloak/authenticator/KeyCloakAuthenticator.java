package com.example.springbootkeycloak.authenticator;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Component
public class KeyCloakAuthenticator {

  private String tokenURL;
  private String clientId;
  private String clientSecret;
  private String grantType;
  private String username;

  public String getTokenURL() {
    return tokenURL;
  }

  public String getClientId() {
    return clientId;
  }

  public String getClientSecret() {
    return clientSecret;
  }

  public String getGrantType() {
    return grantType;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  private String password;

  public KeyCloakAuthenticator tokenURL(String val){
    this.tokenURL = val;
    return this;
  }

  public KeyCloakAuthenticator clientID(String val){
    this.clientId = val;
    return this;
  }

  public KeyCloakAuthenticator clientSecret(String val){
    this.clientSecret = val;
    return this;
  }

  public KeyCloakAuthenticator grantType(String val){
    this.grantType = val;
    return this;
  }

  public KeyCloakAuthenticator username(String val){
    this.username = val;
    return this;
  }

  public KeyCloakAuthenticator password(String val){
    this.password = val;
    return this;
  }

  public static KeyCloakAuthenticator builder(){
    return new KeyCloakAuthenticator();
  }

  public KeyCloakAuthenticator build(){
    return this;
  }
  public ResponseEntity<AuthResponse> authenticate(){

    RestClient restClient = RestClient.builder().build();

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("client_id",clientId);
    map.add("client_secret",clientSecret);
    map.add("grant_type",grantType);
    map.add("username",username);
    map.add("password",password);

    return restClient
      .post()
      .uri("http://localhost:8080/realms/SpringSSO/protocol/openid-connect/token")
      .contentType(MediaType.APPLICATION_FORM_URLENCODED)
      .body(map)
      .retrieve()
     .toEntity(AuthResponse.class);
  }

}
