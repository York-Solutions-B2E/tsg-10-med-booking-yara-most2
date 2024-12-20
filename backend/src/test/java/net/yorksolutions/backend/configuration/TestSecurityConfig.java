package net.yorksolutions.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class TestSecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

//        http.authorizeHttpRequests((auth) -> auth
//                .requestMatchers("/", "/index.html", "/static/**",
//                        "/*.ico", "/*.json", "/*.png", "/api/**", "/login/oauth2/code/okta", "/api/logout","doctors/s",
//                        "/doctors", "/appointments/**", "/api/patients/**", "/specializations/**", "/error").permitAll()
//                .anyRequest().authenticated()
//        );
        http.csrf((csrf) -> csrf.disable()).
                authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                );
//        http.addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }
}
