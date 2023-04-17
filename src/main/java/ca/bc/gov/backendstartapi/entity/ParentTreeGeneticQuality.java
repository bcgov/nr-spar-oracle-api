package ca.bc.gov.backendstartapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/** This class represents a Genetic Quality of an {@link ParentTree} in the database. */
@Getter
@Setter
@Entity
@Table(name = "PARENT_TREE_GENETIC_QUALITY")
@Schema(
    description =
        """
        The calculated genetic quality for Parent Trees by assessment age, assessment year and
        seed planning unit.
        """)
public class ParentTreeGeneticQuality {

  @Id
  @Column(name = "GENETIC_QUALITY_ID")
  @Schema(
      description =
          "An identifier for a Parent Tree and the associated genetic quality test results.",
      example = "2563")
  private Long id;

  @Column(name = "PARENT_TREE_ID", nullable = false)
  @Schema(description = "A unique identifier for each Parent Tree.", example = "4032")
  private Long parentTreeId;

  @Column(name = "SEED_PLAN_UNIT_ID", nullable = false)
  @Schema(
      description = "A unique identifier which is assigned to a Seed Planning Unit.",
      example = "7")
  private Long seedPlaningUnitId;

  @Column(name = "GENETIC_TYPE_CODE", nullable = false, length = 2)
  @Schema(
      description =
          """
          Describes the comparative measure of genetic value for a specific genetic trait of a
          parent tree. Examples are BV (Breeding Value) and CV (Clonal Value).
          """,
      example = "BV")
  private String geneticTypeCode;

  @Column(name = "GENETIC_WORTH_CODE", nullable = false, length = 3)
  @Schema(description = "A code describing various Genetic Worths.", example = "GVO")
  private String geneticWorthCode;

  @Column(name = "GENETIC_QUALITY_VALUE", nullable = false, precision = 3, scale = 1)
  @Schema(
      description =
          """
          The genetic quality value based on the test assessment for a Parent Tree from a test
          no. and series.
          """,
      example = "18")
  private BigDecimal geneticQualityValue;

  @Column(name = "GENETIC_WORTH_CALC_IND", nullable = false)
  @Schema(
      description =
          """
          Indicates that the GQ value will be used in Genetic Worth calculations if the associated
          Parent Tree is used in Seedlot composition. Only one GQ Value can be designated as such
          per unique combination of Seed Plan Unit, Genetic Type and Genetic Trait.
          """)
  private Character geneticWorthCalcInd;
}
