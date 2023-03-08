package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.Orchard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** This repository class contains methods to retrieve Orchards from the database. */
public interface OrchardRepository extends JpaRepository<Orchard, String> {

  @Query("from Orchard o where o.stageCode != 'RET' and o.id = ?1")
  Optional<Orchard> findNotRetiredById(String id);
}
