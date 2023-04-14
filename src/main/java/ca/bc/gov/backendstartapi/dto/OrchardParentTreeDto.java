package ca.bc.gov.backendstartapi.dto;

import java.util.ArrayList;import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrchardParentTreeDto {

  private String orchardId;
  private String vegetationCode;
  private Long seedPlanUnitId;
  private List<ParentTreeDto> parentTrees;

  public OrchardParentTreeDto() {
    this.parentTrees = new ArrayList<>();
  }
}
