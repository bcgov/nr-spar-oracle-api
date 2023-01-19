package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.VegetationCode;
import org.springframework.data.jpa.repository.JpaRepository;

/** A {@link JpaRepository} for {@link VegetationCode}. */
public interface VegetationCodeJpaRepository extends JpaRepository<VegetationCode, String> {}
