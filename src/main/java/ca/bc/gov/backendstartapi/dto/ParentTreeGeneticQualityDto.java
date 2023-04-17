package ca.bc.gov.backendstartapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class ParentTreeGeneticQualityDto {

  @JsonIgnore private Long parentTreeId;

  private String geneticTypeCode;
  private String geneticWorthCode;
  private BigDecimal geneticQualityValue;
}
