package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.FundingSource;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** This interface enables the funding source entity to be retrieved from the database. */
public interface FundingSourceRepository extends JpaRepository<FundingSource, String> {

  @Query(
      value =
          "select fs.* from SPAR_FUND_SRCE_CODE fs WHERE CURRENT_DATE >= fs.EFFECTIVE_DATE "
              + "AND CURRENT_DATE < fs.EXPIRY_DATE ORDER BY fs.SPAR_FUND_SRCE_CODE",
      nativeQuery = true)
  List<FundingSource> findAllValid();
}
