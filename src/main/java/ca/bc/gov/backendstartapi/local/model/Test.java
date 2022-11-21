package ca.bc.gov.backendstartapi.local.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Just an entity to test the access to the local database.
 *
 * <p>TODO: remove before opening a PR.
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Test {

  @Id @GeneratedValue private Long id;

  @Column private String name;
}
