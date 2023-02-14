package ca.bc.gov.backendstartapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/** This class presents a funding source to an agency seedlot owner. */
@Getter
@Setter
@Entity
@Table(name = "SPAR_FUND_SRCE_CODE")
public class FundingSource {

  @Id
  @Column(name = "SPAR_FUND_SRCE_CODE")
  private String code;

  @Column(name = "DESCRIPTION")
  private String description;

  @Column(name = "EFFECTIVE_DATE")
  private LocalDate effectiveDate;

  @Column(name = "EXPIRY_DATE")
  private LocalDate expiryDate;
}
