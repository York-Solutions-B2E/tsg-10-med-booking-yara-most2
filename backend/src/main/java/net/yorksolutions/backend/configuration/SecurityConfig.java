package net.yorksolutions.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/index.html", "/static/**",
                        "/*.ico", "/*.json", "/*.png", "/api/**", "/login/oauth2/code/okta", "/api/logout","doctors/s",
                        "/doctors", "/appointments/**", "/api/patients/**", "/specializations/**", "/error").permitAll()
                .anyRequest().authenticated()
        );
        http.csrf((csrf) -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                // https://stackoverflow.com/a/74521360/65681
                .csrfTokenRequestHandler(new CsrfTokenRequestAttributeHandler())
                .ignoringRequestMatchers("/api/appointments/**", "/api/patients/**") // permit public access for POST, PUT, DELETE
        );
//        http.csrf((csrf) -> csrf.disable()
//        );
        http.addFilterAfter(new CookieCsrfFilter(), BasicAuthenticationFilter.class);
//        http.oauth2Login(Customizer.withDefaults());
        http.oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/", true) // Redirect to frontend after success
        );

        return http.build();
    }

}
