package ca.bc.gov.backendstartapi.config.prod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/** Configurations for authentication in REST endpoints. */
@Configuration
@EnableWebSecurity
@ConditionalOnProperty(name = "authentication.enabled", havingValue = "true", matchIfMissing = true)
public class WebSecurityConfig {

  @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
  String jwkSetUri;

  /**
   * Filters a request to add security checks and configurations.
   *
   * @param http instance of HttpSecurity containing the request.
   * @return SecurityFilterChain with allowed endpoints and all configuration.
   * @throws Exception due to bad configuration possibilities.
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/api/**")
        .authenticated()
        .requestMatchers(HttpMethod.OPTIONS, "/**")
        .permitAll()
        .anyRequest()
        .permitAll()
        .and()
        .httpBasic()
        .disable()
        .formLogin()
        .disable()
        .oauth2ResourceServer()
        .jwt(jwt -> jwt.jwtAuthenticationConverter(converter()).jwkSetUri(jwkSetUri));

    return http.build();
  }

  private Converter<Jwt, AbstractAuthenticationToken> converter() {
    JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
    converter.setJwtGrantedAuthoritiesConverter(roleConverter);
    return converter;
  }

  /**
   * Parse the roles of a client from the JWT, if they're present; if not, subjects with service
   * accounts are granted read and write permissions.
   */
  private final Converter<Jwt, Collection<GrantedAuthority>> roleConverter =
      jwt -> {
        if (!jwt.getClaims().containsKey("client_roles")) {
          String sub = String.valueOf(jwt.getClaims().get("sub"));
          return (sub.startsWith("service-account-nr-fsa"))
              ? List.of(
                  new SimpleGrantedAuthority("ROLE_user_read"),
                  new SimpleGrantedAuthority("ROLE_user_write"))
              : List.of();
        }
        final List<String> realmAccess = (ArrayList<String>) jwt.getClaims().get("client_roles");
        return realmAccess.stream()
            .map(roleName -> "ROLE_" + roleName)
            .map(roleName -> (GrantedAuthority) new SimpleGrantedAuthority(roleName))
            .toList();
      };
}
