package ca.bc.gov.backendstartapi.local;

import ca.bc.gov.backendstartapi.local.model.Test;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/** Configuration for the local database — a PostgreSQL database versioned with Flyway. */
@Configuration(proxyBeanMethods = false)
@EnableJpaRepositories(
    entityManagerFactoryRef = "localEntityManagerFactory",
    basePackages = "ca.bc.gov.backendstartapi.local")
public class LocalDataSourceConfiguration {

  /** TODO: document this method. */
  @Bean(name = "localDataSource")
  @Primary
  @ConfigurationProperties("app.datasource.local")
  public DataSource localDataSource() {
    return DataSourceBuilder.create().build();
  }

  /** TODO: document this method. */
  @Bean(name = "localEntityManagerFactory")
  @Primary
  public LocalContainerEntityManagerFactoryBean localEntityManagerFactory(
      final EntityManagerFactoryBuilder builder,
      @Qualifier("localDataSource") DataSource dataSource) {
    return builder
        .dataSource(dataSource)
        .packages(Test.class.getPackageName())
        .persistenceUnit("local")
        .build();
  }
}
