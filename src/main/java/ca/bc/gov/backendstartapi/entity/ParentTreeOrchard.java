package ca.bc.gov.backendstartapi.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PARENT_TREE_ORCHARD")
public class ParentTreeOrchard {

  @Id
  @Column(name = "PARENT_TREE_ID")
  @Schema(description = "A unique identifier for each Parent Tree.", example = "4032")
  private Long parentTreeId;

  @Column(name = "ORCHARD_ID")
  @Schema(
      description =
          "A unique identifier which is assigned to a location where cuttings or A class seed is produced.",
      example = "405")
  private String orchardId;
}
