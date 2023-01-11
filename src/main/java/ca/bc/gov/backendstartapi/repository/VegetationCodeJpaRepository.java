package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.VegetationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** A {@link JpaRepository} for {@link VegetationCode}. */
@Repository
public interface VegetationCodeJpaRepository extends JpaRepository<VegetationCode, String> {}
