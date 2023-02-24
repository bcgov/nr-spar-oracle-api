package ca.bc.gov.backendstartapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Represents a Funding Source object in the database")
public class FundingSource {

  @Id
  @Column(name = "SPAR_FUND_SRCE_CODE")
  @Schema(description = "Funding source's code, from SPAR_FUND_SRCE_CODE column", example = "BCT")
  private String code;

  @Column(name = "DESCRIPTION")
  @Schema(
      description = "Funding source's description, from DESCRIPTION column",
      example = "BC Timber Sales")
  private String description;

  @Column(name = "EFFECTIVE_DATE")
  @Schema(
      description = "Funding source's effective date.",
      type = "string",
      format = "date")
  private LocalDate effectiveDate;

  @Column(name = "EXPIRY_DATE")
  @Schema(
      description = "Funding source's expiry date.",
      type = "string",
      format = "date")
  private LocalDate expiryDate;
}
