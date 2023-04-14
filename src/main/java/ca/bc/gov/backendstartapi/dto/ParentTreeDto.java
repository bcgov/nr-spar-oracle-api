package ca.bc.gov.backendstartapi.dto;

import java.util.ArrayList;import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ParentTreeDto {

  private Long parentTreeId;
  private String parentTreeNumber;
  private String parentTreeRegStatusCode;
  private boolean active;
  private boolean tested;
  private boolean breedingProgram;
  private Long femaleParentTreeId;
  private Long maleParentTreeId;
  private List<ParentTreeGeneticQualityDto> parentTreeGeneticQualities;

  public ParentTreeDto() {
    this.parentTreeGeneticQualities = new ArrayList<>();
  }

}
