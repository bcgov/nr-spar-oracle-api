package ca.bc.gov.backendstartapi.config.dev;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Disables authorisation in annotated REST endpoints.
 *
 * @see EnableMethodSecurity
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = false)
@ConditionalOnProperty(value = "authorisation.enabled", havingValue = "false")
public class MethodSecurityConfig {}
