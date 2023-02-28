package ca.bc.gov.backendstartapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an Orchard. A location where class A seed or class A cuttings are produced.
 */
@Getter
@Setter
@Entity
@Table(name = "ORCHARD")
@Schema(
    description =
        """
        Represents an Orchard object in the database, a location where class A seed or
        class A cuttings are produced.
        """)
public class Orchard {

  @Id
  @Column(name = "ORCHARD_ID")
  @Schema(
      description =
          """
          A unique identifier which is assigned to a location where cuttings or
          A class seed is produced.
          """,
      example = "339")
  private String id;

  @Column(name = "ORCHARD_NAME")
  @Schema(
      description = "The name of a location where cuttings or A class seed is produced.",
      example = "EAGLEROCK")
  private String name;

  @Column(name = "VEGETATION_CODE")
  @Schema(description = "A code which represents a species of tree or brush.", example = "PLI")
  private String vegetationCode;

  @Column(name = "ORCHARD_LOT_TYPE_CODE")
  @Schema(
      description =
          """
          A code representing a type of orchard. The two values will be 'S' (Seed Lot) or
          'C' (Cutting Lot).
          """,
      example = "S")
  private Character lotTypeCode;

  @Column(name = "ORCHARD_LOT_TYPE_DESCRIPTION")
  @Schema(
      description =
          """
          A description of the Orchard Lot Type code. The two values will be 'Seed Lot'
          or 'Cutting Lot'.
          """,
      example = "Seed Lot")
  private String lotTypeDescription;

  @Column(name = "ORCHARD_STAGE_CODE")
  @Schema(
      description = "A code which represents the current stage or status of an orchard.",
      example = "PRD")
  private String stageCode;
}
