package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.ParentTreeOrchard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** This class holds methods for retrieving {@link ParentTreeOrchard} data from the database. */
public interface ParentTreeOrchardRepository extends JpaRepository<ParentTreeOrchard, Long> {

  List<ParentTreeOrchard> findAllByOrchardId(String orchardId);
}
