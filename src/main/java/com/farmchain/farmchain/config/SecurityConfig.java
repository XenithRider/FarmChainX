package com.farmchain.farmchain.config;

import com.farmchain.farmchain.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // 🔒 Disable CSRF since we use JWT (stateless)
                .csrf(csrf -> csrf.disable())

                // 🔒 Stateless session (JWT-based)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // ⚙️ Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // 🌍 Public routes — open to everyone (no login required)
                        .requestMatchers(
                                "/api/auth/**",                   // login/register
                                "/uploads/**",                     // images, static files
                                "/api/verify/**",                  // QR scan verification (public + token-supported)
                                "/api/products/*/qrcode/download"  // QR image download
                        ).permitAll()

                        // 👨‍🌾 Product endpoints — FARMER + supply chain roles
                        .requestMatchers("/api/products/**")
                        .hasAnyRole("FARMER", "DISTRIBUTER", "RETAILER", "ADMIN")

                        // 🚚 Tracking endpoints — only DISTRIBUTER, RETAILER, ADMIN
                        .requestMatchers("/api/track/**")
                        .hasAnyRole("DISTRIBUTER", "RETAILER", "ADMIN")

                        // 🧑‍💼 Admin-only endpoints
                        .requestMatchers("/api/admin/**")
                        .hasRole("ADMIN")

                        // 🔐 Everything else → must be authenticated
                        .anyRequest().authenticated()
                )

                // 🧩 Add JWT filter before username-password auth filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}