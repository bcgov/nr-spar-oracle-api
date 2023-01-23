package ca.bc.gov.backendstartapi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.beans.Transient;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.NamedQuery;

/**
 * Code used to identify various vegetation species, along with metadata.
 *
 * <p>All date fields are stored as in the database as Oracle date types, which also store
 * information about time down to seconds and without timezones (see <a
 * href="https://stackoverflow.com/a/13568348">here</a>).
 */
@Entity
@Table(name = "vegetation_code")
@AllArgsConstructor
@Getter
@ToString
@NamedQuery(
    name = VegetationCode.FIND_VALID_BY_CODE_OR_DESCRIPTION,
    query =
        "SELECT vc FROM VegetationCode vc "
            + "WHERE (vc.id ILIKE :search OR vc.description ILIKE :search) "
            + "AND CURRENT_DATE >= vc.effectiveDate AND CURRENT_DATE < vc.expiryDate "
            + "ORDER BY vc.id")
@Schema(description = "Code used to identify various vegetation species, along with metadata.")
public class VegetationCode {

  public static final String FIND_VALID_BY_CODE_OR_DESCRIPTION =
      "VegetationCode.findValidByCodeOrDescription";

  /** The vegetation code itself. */
  @Id
  @Column(name = "vegetation_code", length = 8, nullable = false)
  @JsonProperty("code")
  @Schema(description = "The code itself, considered its identifier.")
  private String id;

  /** Description for the affiliated code. */
  @Column(length = 120, nullable = false)
  @Schema(description = "Description for the affiliated code.")
  private String description;

  /** The date from which the code is in effect. */
  @Column(name = "effective_date", nullable = false)
  @Schema(description = "The date from which the code is in effect.")
  private LocalDate effectiveDate;

  /** The date on which the code expires. */
  @Column(name = "expiry_date", nullable = false)
  @Schema(description = "The date on which the code expires.")
  private LocalDate expiryDate;

  /** The date and time of the last update. */
  @Column(name = "update_timestamp", nullable = false)
  @Schema(description = "The date and time when the code was last updated.")
  private LocalDateTime updateTimestamp;

  /** Because we need an empty constructor for Hibernate. */
  private VegetationCode() {}

  @Transient
  public boolean isValid() {
    var today = LocalDate.now();
    return !effectiveDate.isAfter(today) && expiryDate.isAfter(today);
  }
}
