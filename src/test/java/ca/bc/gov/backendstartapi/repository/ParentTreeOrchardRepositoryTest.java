package ca.bc.gov.backendstartapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.bc.gov.backendstartapi.entity.ParentTreeOrchard;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ParentTreeOrchardRepositoryTest {

  @Autowired private ParentTreeOrchardRepository parentTreeOrchardRepository;

  @Test
  @DisplayName("findAllByOrchardIdTest")
  @Sql(scripts = {"classpath:scripts/ParentTreeOrchardRepository.sql"})
  void findAllByOrchardIdTest() {
    List<ParentTreeOrchard> orchards = parentTreeOrchardRepository.findByIdOrchardId("407");

    assertFalse(orchards.isEmpty());
    assertEquals(3, orchards.size());

    ParentTreeOrchard parentTreeOrchard = orchards.get(0);

    assertEquals(4032L, parentTreeOrchard.getId().getParentTreeId());
    assertEquals("407", parentTreeOrchard.getId().getOrchardId());
  }

  @Test
  @DisplayName("findAllByOrchardIdEmptyTest")
  void findAllByOrchardIdEmptyTest() {
    List<ParentTreeOrchard> orchards = parentTreeOrchardRepository.findByIdOrchardId("123");

    assertTrue(orchards.isEmpty());
  }
}
