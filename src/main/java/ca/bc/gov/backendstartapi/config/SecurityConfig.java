package ca.bc.gov.backendstartapi.config;

import ca.bc.gov.backendstartapi.util.ObjectUtil;
import com.nimbusds.jose.shaded.json.JSONArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

/** This class contains all configurations related to security and authentication. */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

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
        .authorizeRequests()
        .antMatchers("/api/**")
        .authenticated()
        .antMatchers(HttpMethod.OPTIONS, "/**")
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
    converter.setJwtGrantedAuthoritiesConverter(roleConverter());
    return converter;
  }

  private Converter<Jwt, Collection<GrantedAuthority>> roleConverter() {
    return jwt -> {
      final JSONArray realmAccess = (JSONArray) jwt.getClaims().get("client_roles");
      List<GrantedAuthority> authorities = new ArrayList<>();
      if (ObjectUtil.isEmptyOrNull(realmAccess)) {
        String sub = String.valueOf(jwt.getClaims().get("sub"));
        if (sub.startsWith("service-account-nr-fsa")) {
          authorities.add(new SimpleGrantedAuthority("ROLE_user_read"));
          authorities.add(new SimpleGrantedAuthority("ROLE_user_write"));
        }
        return authorities;
      }
      realmAccess.stream()
          .map(roleName -> "ROLE_" + roleName)
          .map(SimpleGrantedAuthority::new)
          .forEach(authorities::add);
      return authorities;
    };
  }
}
