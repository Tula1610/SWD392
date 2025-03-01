//package com.example.backend.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        String[] publicEndpoints = {
//                "/login", "/", "/api/users", "/api/users/**",
//                "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "swagger-ui-custom"
//        };
//        http.cors(cors -> cors.disable())
//                .csrf(csrf -> csrf.disable())
//                .sessionManagement(session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(publicEndpoints).permitAll()
//                        .anyRequest().authenticated());
//        return http.build();
//    }
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        var user = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("admin123") // Match application.properties
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
////    @Bean
////    public CorsConfigurationSource corsConfigurationSource() {
////        CorsConfiguration configuration = new CorsConfiguration();
////        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Adjust as needed
////        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
////        configuration.setAllowedHeaders(List.of("*"));
////        configuration.setAllowCredentials(true);
////
////        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////        source.registerCorsConfiguration("/**", configuration);
////        return source;
////    }
//}