package com.ai360.ecommerce.security;

import com.ai360.ecommerce.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * SECURITY CONFIG
 * ================
 * Cau hinh Spring Security cho JWT-based authentication.
 *
 * Luong xac thuc:
 * 1. Request den -> JwtAuthFilter chay truoc
 * 2. Filter lay token tu header "Authorization: Bearer <token>"
 * 3. Xac thuc token bang JwtUtil
 * 4. Neu hop le -> set Authentication vao SecurityContext
 * 5. Controller chay voi dung user da xac thuc
 */

// -------------------------------------------------------
// JWT FILTER — chay 1 lan moi request
// -------------------------------------------------------
@Component
@RequiredArgsConstructor
class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Lay header Authorization
        String authHeader = request.getHeader("Authorization");

        // Token phai bat dau bang "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // bo "Bearer "

        if (jwtUtil.validateToken(token)) {
            String email = jwtUtil.getEmailFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Tao Authentication object va set vao SecurityContext
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}

// -------------------------------------------------------
// SECURITY CONFIGURATION
// -------------------------------------------------------
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Autowired private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Tat CSRF vi dung JWT (stateless)
            .csrf(csrf -> csrf.disable())

            // Cau hinh quyen truy cap tung endpoint
            .authorizeHttpRequests(auth -> auth
                // Public: ai cung truy cap duoc
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
                // H2 console va Swagger (chi dung khi dev)
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/api-docs/**").permitAll()
                // Con lai phai dang nhap
                .anyRequest().authenticated()
            )

            // Khong luu session — moi request phai mang theo token
            .sessionManagement(sess ->
                sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Cho phep H2 console hien thi trong iframe
            .headers(headers ->
                headers.frameOptions(frame -> frame.sameOrigin()))

            // Them JWT filter truoc filter mac dinh cua Spring
            .addFilterBefore(jwtAuthFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // BCrypt: hash password an toan (tu dong them salt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager: dung de xac thuc username/password luc dang nhap
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

// -------------------------------------------------------
// USER DETAILS SERVICE — load user tu database cho Spring Security
// -------------------------------------------------------
@Component
@RequiredArgsConstructor
class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.ai360.ecommerce.entity.User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User khong ton tai: " + email));

        // Chuyen entity User thanh Spring Security UserDetails
        return new User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(
                    new org.springframework.security.core.authority.SimpleGrantedAuthority(
                        "ROLE_" + user.getRole().name()
                    )
                )
        );
    }
}
