package ca.bc.gov.backendstartapi.implementation.repository;

import ca.bc.gov.backendstartapi.implementation.entity.VegetationCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** A {@link JpaRepository} for {@link VegetationCodeEntity}. */
@Repository
public interface VegetationCodeJpaRepository extends JpaRepository<VegetationCodeEntity, String> {}
