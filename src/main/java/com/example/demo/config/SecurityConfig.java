package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of("/http://localhost:5173/"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        corsConfiguration.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        return new InMemoryUserDetailsManager(
                User.withUsername("batuhan")
                        .password(passwordEncoder.encode("1234"))
                        .roles("ADMIN")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    // Public endpoints (accessible without authentication)
                    auth.requestMatchers("/categories/**").permitAll();
                    auth.requestMatchers("/products/**").permitAll();
                    auth.requestMatchers("/users/**").permitAll();

                    // Categories access control
                    auth.requestMatchers(HttpMethod.GET, "/categories/**")
                            .hasAnyAuthority("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.POST, "/categories/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/categories/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/categories/**").hasAuthority("ADMIN");

                    // Products access control
                    auth.requestMatchers(HttpMethod.GET, "/products/**")
                            .hasAnyAuthority("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.POST, "/products/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/products/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/products/**").hasAuthority("ADMIN");

                    // Users access control
                    auth.requestMatchers(HttpMethod.GET, "/users/**")
                            .hasAnyAuthority("ADMIN", "USER");
                    auth.requestMatchers(HttpMethod.POST, "/users/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.PUT, "/users/**").hasAuthority("ADMIN");
                    auth.requestMatchers(HttpMethod.DELETE, "/users/**").hasAuthority("ADMIN");

                    auth.anyRequest().permitAll();
                })
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}