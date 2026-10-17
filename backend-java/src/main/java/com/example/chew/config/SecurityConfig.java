
package com.example.chew.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
            // Mantener CORS del proyecto
            .cors(Customizer.withDefaults())

            // Temporal para API REST academica
            .csrf(AbstractHttpConfigurer::disable)

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // Consultar productos sin autenticacion
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/platos",
                    "/api/platos/**",
                    "/api/bebidas",
                    "/api/bebidas/**"
                ).permitAll()

                // Modificar productos: solo ADMIN
                .requestMatchers(
                    "/api/platos",
                    "/api/platos/**",
                    "/api/bebidas",
                    "/api/bebidas/**"
                ).hasRole("ADMIN")

                // Conservar las demas API existentes
                .requestMatchers("/api/**").permitAll()

                .anyRequest().permitAll()
            )

            // Autenticacion sin JWT
            .httpBasic(Customizer.withDefaults())
            .formLogin(AbstractHttpConfigurer::disable);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder encoder,
            @Value("${CHEW_ADMIN_USER}") String username,
            @Value("${CHEW_ADMIN_PASSWORD}") String password) {

        UserDetails administrador = User.builder()
                .username(username)
                .password(encoder.encode(password))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(administrador);
    }
}
