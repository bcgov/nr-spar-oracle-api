package ca.bc.gov.backendstartapi.config.prod;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

/**
 * Enables authorisation in annotated REST endpoints.
 *
 * @see EnableMethodSecurity
 */
@Configuration
@EnableMethodSecurity
@ConditionalOnProperty(value = "authorisation.enabled", havingValue = "true", matchIfMissing = true)
public class MethodSecurityConfig {}
