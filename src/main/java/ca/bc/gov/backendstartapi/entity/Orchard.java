package ca.bc.gov.backendstartapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents an Orchard. A location where class A seed or class A cuttings are produced.
 */
@Getter
@Setter
@Entity
@Table(name = "ORCHARD")
public class Orchard {

  @Id
  @Column(name = "ORCHARD_ID", length = 3)
  private String id;

  @Column(name = "ORCHARD_NAME", length = 30)
  private String name;

  @Column(name = "VEGETATION_CODE", length = 8)
  private String vegetationCode;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "ORCHARD_LOT_TYPE_CODE", referencedColumnName = "ORCHARD_LOT_TYPE_CODE")
  private OrchardLotTypeCode orchardLotTypeCode;

  @Column(name = "ORCHARD_STAGE_CODE", length = 3)
  private String stageCode;
}
