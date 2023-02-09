package ca.bc.gov.backendstartapi.config.prod;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Enables authorisation in annotated REST endpoints.
 *
 * @see EnableMethodSecurity
 */
@Configuration
@Profile("!dev")
@EnableMethodSecurity
public class MethodSecurityConfig {}
