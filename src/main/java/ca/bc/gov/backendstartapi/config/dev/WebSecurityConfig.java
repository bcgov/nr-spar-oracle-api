package ca.bc.gov.backendstartapi.config.dev;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/**
 * Configurations for authentication in REST endpoints.
 *
 * <p>To be used for <strong>local development only.</strong>
 */
@Configuration
@Profile("dev")
@EnableWebSecurity
public class WebSecurityConfig {

  /** Authorize all HTTP requests. */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .authorizeHttpRequests()
        .anyRequest()
        .permitAll();
    return http.build();
  }
}
