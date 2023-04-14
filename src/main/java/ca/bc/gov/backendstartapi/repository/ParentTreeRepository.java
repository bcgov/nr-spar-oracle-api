package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.ParentTree;
import java.util.List;import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParentTreeRepository extends JpaRepository<ParentTree, Long> {

  @Query("from ParentTree where id in ?1")
  List<ParentTree> findAllIn(List<Long> ids);
}
