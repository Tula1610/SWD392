package com.example.backend.security;


import com.example.backend.model.mongoDB.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class CustomFilterSecurity {

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    CustomJwtFilter customJwtFilter;

    @Autowired
    UserRepository userRepository;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] publicUrls = { "/login/**", "/login.html",
                "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
                "/swagger-ui-custom",
                "/oauth2/authorization/google",
                "/login/oauth2/code/google",
                "/login/google",
                "/v3/api-docs/**" };

        String[] adminUrls = { "/api/users/**", "/index.html" };

        http
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(publicUrls).permitAll()
                .requestMatchers(adminUrls).hasRole("ADMIN") // Yêu cầu role ADMIN cho index.html
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .defaultSuccessUrl("/index.html", true)
                .permitAll()
                .and()
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(this.oidcUserService()))
                        .successHandler((request, response, authentication) -> {
                            OidcUser oidcUser = (OidcUser) authentication.getPrincipal();
                            boolean isAdmin = oidcUser.getAuthorities().stream()
                                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
                            if (isAdmin) {
                                response.sendRedirect("/index.html");
                            } else {
                                response.sendRedirect("/login/google");
                            }
                        })
                        .permitAll()
                );

        http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
        OidcUserService delegate = new OidcUserService();

        return userRequest -> {
            OidcUser oidcUser = delegate.loadUser(userRequest);
            String email = oidcUser.getEmail();

            // Tìm user trong database dựa trên email
            User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("User with email " + email + " not found in the system");
            }

            // Chuyển đổi authorities từ oidcUser thành List<SimpleGrantedAuthority>
            List<SimpleGrantedAuthority> authorities = oidcUser.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                    .collect(Collectors.toList());

            // Thêm quyền ROLE_ADMIN hoặc ROLE_USER dựa trên role trong database
            authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

            // Trả về DefaultOidcUser với danh sách quyền mới
            return new DefaultOidcUser(authorities, oidcUser.getIdToken(), oidcUser.getUserInfo());
        };
    }


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        String[] list = { "/login/**", "/api/users/**",
//                 "/api-docs/**", "/swagger-ui/**", "/swagger-ui.html",
//                "/swagger-ui-custom" };
//
//
//        // http: là nơi định nghĩa cái rule, tức là link nào được phép hoặc không được
//        // phép
//        // csrf: là lợi dụng người dùng đăng nhập vào trang web hợp lệ để gửi những yêu
//        // cầu trái phép
//        http.cors().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeHttpRequests()
//                .requestMatchers(list).permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .oauth2Login(Customizer.withDefaults());
//
//        // Cấu hình OAuth2 login nếu cần
//
//        http.addFilterBefore(customJwtFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
//        return NoOpPasswordEncoder.getInstance();
    }

}
