package ca.bc.gov.backendstartapi.entity;

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
public class ParentTreeGeneticQuality {

  @Id
  @Column(name = "GENETIC_QUALITY_ID")
  private Long id;

  @Column(name = "PARENT_TREE_ID", nullable = false)
  private Long parentTreeId;

  @Column(name = "SEED_PLAN_UNIT_ID", nullable = false)
  private Long seedPlaningUnitId;

  @Column(name = "GENETIC_TYPE_CODE", nullable = false, length = 2)
  private String geneticTypeCode;

  @Column(name = "GENETIC_WORTH_CODE", nullable = false, length = 3)
  private String geneticWorthCode;

  @Column(name = "GENETIC_QUALITY_VALUE", nullable = false, precision = 3, scale = 1)
  private BigDecimal geneticQualityValue;

  @Column(name = "GENETIC_WORTH_CALC_IND", nullable = false)
  private Character geneticWorthCalcInd;
}
