package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.ParentTreeOrchard;
import java.util.List;import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentTreeOrchardRepository extends JpaRepository<ParentTreeOrchard, Long> {

  List<ParentTreeOrchard> findAllByOrchardId(String orchardId);
}
