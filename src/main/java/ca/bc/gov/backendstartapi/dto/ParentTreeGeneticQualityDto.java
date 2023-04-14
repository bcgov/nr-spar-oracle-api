package ca.bc.gov.backendstartapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParentTreeGeneticQualityDto {

  @JsonIgnore
  private Long parentTreeId;

  private String geneticTypeCode;
  private String geneticWorthCode;
  private BigDecimal geneticQualityValue;

}
