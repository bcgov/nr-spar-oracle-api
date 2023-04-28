package ca.bc.gov.backendstartapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class holds the fields that will be returned from a {@link
 * ca.bc.gov.backendstartapi.entity.ParentTreeGeneticQuality} of a{@link
 * ca.bc.gov.backendstartapi.entity.ParentTree}.
 */
@Getter
@Setter
@ToString
@Schema(
    description =
        """
        The calculated genetic quality for Parent Trees by assessment age, assessment year and
        seed planning unit.
        """)
public class ParentTreeGeneticQualityDto {

  @JsonIgnore private Long parentTreeId;

  @Schema(
      description =
          """
          Describes the comparative measure of genetic value for a specific genetic trait of a
          parent tree. Examples are BV (Breeding Value) and CV (Clonal Value).
          """,
      example = "BV")
  private String geneticTypeCode;

  @Schema(description = "A code describing various Genetic Worths.", example = "GVO")
  private String geneticWorthCode;

  @Schema(
      description =
          """
          The genetic quality value based on the test assessment for a Parent Tree from a test
          no. and series.
          """,
      example = "18")
  private BigDecimal geneticQualityValue;
}
