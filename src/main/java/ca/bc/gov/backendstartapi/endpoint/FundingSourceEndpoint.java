package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.entity.FundingSource;
import ca.bc.gov.backendstartapi.repository.FundingSourceRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** This class exposes funding sources resources API. */
@Setter
@RestController
@NoArgsConstructor
@RequestMapping("/api/funding-sources")
public class FundingSourceEndpoint {

  private FundingSourceRepository fundingSourceRepository;

  @Autowired
  public FundingSourceEndpoint(FundingSourceRepository fundingSourceRepository) {
    this.fundingSourceRepository = fundingSourceRepository;
  }

  /**
   * Retrieve all funding sources.
   *
   * @return A list of {@link FundingSource} with all found result.
   */
  @GetMapping(produces = "application/json")
  @PreAuthorize("hasRole('user_read')")
  public List<FundingSource> getAllFundingSources() {
    return fundingSourceRepository.findAll().stream()
        .filter(x -> x.getExpiryDate().isAfter(LocalDate.now()))
        .toList();
  }
}
