package ca.bc.gov.backendstartapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** This class represents a Parent Tree of an {@link Orchard} in the database. */
@Getter
@Setter
@Entity
@Table(name = "PARENT_TREE")
@Schema(description = "The geographic location of a each Parent Tree sourced from a natural stand.")
public class ParentTree {

  @Id
  @Column(name = "PARENT_TREE_ID", length = 10)
  @Schema(description = "A unique identifier for each Parent Tree.", example = "4032")
  private Long id;

  @Column(name = "PARENT_TREE_NUMBER", length = 5, nullable = false)
  @Schema(
      description =
          """
          The original registration number given to a Parent Tree in conjunction with a Species
          Code.
          """,
      example = "37")
  private String parentTreeNumber;

  @Column(name = "VEGETATION_CODE", length = 8, nullable = false)
  @Schema(description = "A code describing various Vegetation Species.", example = "ACT")
  private String vegetationCode;

  @Column(name = "PARENT_TREE_REG_STATUS_CODE", length = 3, nullable = false)
  @Schema(
      description = "The code that indicates the status of the Parent Tree registration",
      example = "APP")
  private String parentTreeRegStatusCode;

  @Column(name = "LOCAL_NUMBER", length = 20)
  @Schema(
      description = "A number that has historically been used to identify a Parent Tree.",
      example = "123456")
  private String localNumber;

  @Column(name = "ACTIVE_IND")
  @Schema(
      description = "Indicates whether the parent tree selection is active or inactive.",
      example = "Y")
  private Boolean active;

  @Column(name = "TESTED_IND")
  @Schema(
      description = "Indicates whether the parent tree selection is tested or untested.",
      example = "Y")
  private Boolean tested;

  @Column(name = "BREEDING_PROGRAM_IND")
  @Schema(
      description =
          "A code indicating if a parent tree is included in the forest genetics breeding program.",
      example = "Y")
  private Boolean breedingProgram;

  @Column(name = "FEMALE_PARENT_PARENT_TREE_ID")
  @Schema(description = "A unique identifier for each Parent Tree.", example = "123")
  private Long femaleParentParentTreeId;

  @Column(name = "MALE_PARENT_PARENT_TREE_ID")
  @Schema(description = "A unique identifier for each Parent Tree.", example = "123")
  private Long maleParentParentTreeId;
}
