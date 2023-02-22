package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.FundingSource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** This interface enables the funding source entity to be retrieved from the database. */
public interface FundingSourceRepository extends JpaRepository<FundingSource, String> {

  @Query(
      value =
          "select fs from FundingSource fs WHERE CURRENT_DATE >= fs.effectiveDate "
              + "AND CURRENT_DATE < fs.expiryDate ORDER BY fs.code")
  List<FundingSource> findAllValid();
}
