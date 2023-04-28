package ca.bc.gov.backendstartapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class holds the fields that will be returned from a {@link
 * ca.bc.gov.backendstartapi.entity.ParentTree}.
 */
@Getter
@Setter
@ToString
@Schema(description = "The geographic location of a each Parent Tree sourced from a natural stand.")
public class ParentTreeDto {

  @Schema(description = "A unique identifier for each Parent Tree.", example = "4032")
  private Long parentTreeId;

  @Schema(
      description =
          """
          The original registration number given to a Parent Tree in conjunction with a Species
          Code.
          """,
      example = "37")
  private String parentTreeNumber;

  @Schema(
      description = "The code that indicates the status of the Parent Tree registration",
      example = "APP")
  private String parentTreeRegStatusCode;

  @Schema(
      description = "A number that has historically been used to identify a Parent Tree.",
      example = "123456")
  private String localNumber;

  @Schema(
      description = "Indicates whether the parent tree selection is active or inactive.",
      example = "Y")
  private boolean active;

  @Schema(
      description = "Indicates whether the parent tree selection is tested or untested.",
      example = "Y")
  private boolean tested;

  @Schema(
      description =
          "A code indicating if a parent tree is included in the forest genetics breeding program.",
      example = "Y")
  private boolean breedingProgram;

  @Schema(description = "A unique identifier for each Parent Tree.", example = "123")
  private Long femaleParentTreeId;

  @Schema(description = "A unique identifier for each Parent Tree.", example = "123")
  private Long maleParentTreeId;

  private List<ParentTreeGeneticQualityDto> parentTreeGeneticQualities;

  public ParentTreeDto() {
    this.parentTreeGeneticQualities = new ArrayList<>();
  }
}
