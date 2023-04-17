package ca.bc.gov.backendstartapi.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class holds the fields that will be returned when requesting a {@link
 * ca.bc.gov.backendstartapi.entity.ParentTree} from an {@link
 * ca.bc.gov.backendstartapi.entity.Orchard}.
 */
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
