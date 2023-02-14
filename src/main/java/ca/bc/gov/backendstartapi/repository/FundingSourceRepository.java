package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.FundingSource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundingSourceRepository extends JpaRepository<FundingSource, String> {}
