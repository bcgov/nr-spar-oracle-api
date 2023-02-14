package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.FundingSource;
import org.springframework.data.jpa.repository.JpaRepository;

/** This interface enables the funding source entity to be retrieved from the database. */
public interface FundingSourceRepository extends JpaRepository<FundingSource, String> {}
