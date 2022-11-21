package ca.bc.gov.backendstartapi.local;

import ca.bc.gov.backendstartapi.local.model.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Repository for CRUD operation on {@link Test}. */
@Repository
public interface TestRepository extends CrudRepository<Test, Long> {}
