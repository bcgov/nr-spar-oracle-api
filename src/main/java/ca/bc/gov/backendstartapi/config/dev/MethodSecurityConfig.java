package ca.bc.gov.backendstartapi.config.dev;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Disables authorisation in annotated REST endpoints.
 *
 * @see EnableMethodSecurity
 */
@Configuration
@Profile("dev")
@EnableMethodSecurity(prePostEnabled = false)
public class MethodSecurityConfig {}
