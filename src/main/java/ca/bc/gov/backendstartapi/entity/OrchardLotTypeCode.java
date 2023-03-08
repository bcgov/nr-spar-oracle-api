package ca.bc.gov.backendstartapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.beans.Transient;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/** This class represents an {@link Orchard} Lot Type. */
@Getter
@Setter
@Entity
@Table(name = "ORCHARD_LOT_TYPE_CODE")
@Schema(description = "A list of valid Orchard Lot Types Codes.")
public class OrchardLotTypeCode {

  @Id
  @Column(name = "ORCHARD_LOT_TYPE_CODE")
  @Schema(description = "A code describing various Orchard Lot Types.", example = "S")
  private Character code;

  @Column(name = "DESCRIPTION", nullable = false, length = 120)
  @Schema(description = "A description for the affiliated code.", example = "Seed Lot")
  private String description;

  @Column(name = "EFFECTIVE_DATE", nullable = false)
  @Schema(description = "The date from which the code is in effect.")
  private LocalDate effectiveDate;

  @Column(name = "EXPIRY_DATE", nullable = false)
  @Schema(description = "The date the code expires on.")
  private LocalDate expiryDate;

  /** The date and time of the last update. */
  @Column(name = "UPDATE_TIMESTAMP", nullable = false)
  @Schema(description = "The date and time of the last update.")
  private LocalDate updateTimestamp;

  @Transient
  public boolean isValid() {
    var today = LocalDate.now();
    return !effectiveDate.isAfter(today) && expiryDate.isAfter(today);
  }
}
