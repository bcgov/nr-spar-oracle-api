package ca.bc.gov.backendstartapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a relation between {@link ParentTree} and {@link Orchard} in the database.
 */
@Getter
@Setter
@Entity
@Table(name = "PARENT_TREE_ORCHARD")
public class ParentTreeOrchard {

  @Id
  @Column(name = "PARENT_TREE_ID")
  private Long parentTreeId;

  @Column(name = "ORCHARD_ID")
  private String orchardId;
}
