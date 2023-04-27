package ca.bc.gov.backendstartapi.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** This class holds the primary key columns of {@link ParentTreeOrchard}. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ParentTreeOrchardId {

  private Long parentTreeId;
  private String orchardId;
}
