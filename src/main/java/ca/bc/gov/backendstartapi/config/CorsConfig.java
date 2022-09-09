package ca.bc.gov.backendstartapi.config;

import ca.bc.gov.backendstartapi.util.ObjectUtil;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/** This class holds the configuration for CORS handling. */
@Configuration
@EnableWebFlux
@Slf4j
public class CorsConfig implements WebFluxConfigurer {

  @Value("${server.allowed.cors.origins}")
  private String[] allowedOrigins;

  /**
   * Adds CORS mappings and allowed origins.
   *
   * @param registry Spring Cors Registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    if (!ObjectUtil.isEmptyOrNull(allowedOrigins)) {
      log.info("allowedOrigins: {}", Arrays.asList(allowedOrigins));

      registry
          .addMapping("/**")
          .allowedOriginPatterns(allowedOrigins)
          .allowedMethods("GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS", "HEAD");
    }
    WebFluxConfigurer.super.addCorsMappings(registry);
  }
}
