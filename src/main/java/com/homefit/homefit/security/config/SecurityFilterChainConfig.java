package com.homefit.homefit.security.config;

import com.homefit.homefit.security.handler.CustomAuthenticationEntryPoint;
import com.homefit.homefit.security.handler.CustomAuthenticationFailureHandler;
import com.homefit.homefit.security.handler.CustomAuthenticationSuccessHandler;
import com.homefit.homefit.security.handler.CustomLogoutSuccessHandler;
import com.homefit.homefit.security.handler.CustomSessionInformationExpiredStrategy;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableMethodSecurity
@EnableWebSecurity
@Configuration
public class SecurityFilterChainConfig {
	private final SessionRegistry sessionRegistry;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session
                        .maximumSessions(5)
                        .sessionRegistry(sessionRegistry)
                        .expiredSessionStrategy(new CustomSessionInformationExpiredStrategy())
                )
                .formLogin(formLogin -> formLogin
                        .loginProcessingUrl("/auth/sign-in")
                        .successHandler(new CustomAuthenticationSuccessHandler())
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/auth/sign-out")
                        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/verify-code", "/auth/code", "/member/sign-up", "/member/password").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/region").permitAll()
                        .anyRequest().authenticated()
//                        .anyRequest().permitAll()
                )
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .build();
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5173"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return source;
    }
}
