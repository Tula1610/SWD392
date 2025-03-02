package com.example.backend.controller.Login;

import com.example.backend.model.mongoDB.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.util.JwtUtilHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JwtUtilHelper jwtUtilHelper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping
    public ResponseEntity<Void> showLogin() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("/login.html"))
                .build();
    }



    @GetMapping("/google")
    public ResponseEntity<Map<String, Object>> user(@AuthenticationPrincipal OidcUser principal,
                                                    OAuth2AuthenticationToken authentication) {
        if (principal == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }

        OAuth2AuthorizedClient authorizedClient = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(),
                authentication.getName());
        String accessToken = (authorizedClient != null) ? authorizedClient.getAccessToken().getTokenValue() : null;

        String email = principal.getEmail();
        User user = userRepository.findByEmail(email); // Find user by email
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found in database");
        }

        String jwtToken = jwtUtilHelper.genarateToken(email); // Generate JWT with username

        Map<String, Object> response = new HashMap<>();
        response.put("name", principal.getAttribute("name"));
        response.put("email", principal.getEmail());
        response.put("picture", principal.getAttribute("picture"));
        response.put("access_token", accessToken);
        response.put("jwt_token", jwtToken);
        response.put("role", user.getRole().name());
        response.put("redirectUrl", "/index.html");

        return ResponseEntity.ok(response);
    }

}
