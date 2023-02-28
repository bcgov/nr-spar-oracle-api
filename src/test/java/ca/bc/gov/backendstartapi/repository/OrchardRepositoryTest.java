package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.Orchard;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
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
@Sql(scripts = {"classpath:scripts/OrchardRepositoryTest.sql"})
class OrchardRepositoryTest {

  @Autowired private OrchardRepository orchardRepository;

  @Test
  @DisplayName("findByIdSuccessTest")
  void findByIdSuccessTest() {
    Optional<Orchard> orchardPrd = orchardRepository.findById("337");

    Assertions.assertTrue(orchardPrd.isPresent());
    Assertions.assertEquals("GRANDVIEW", orchardPrd.get().getName());
    Assertions.assertEquals("PLI", orchardPrd.get().getVegetationCode());
    Assertions.assertEquals('S', orchardPrd.get().getLotTypeCode());
    Assertions.assertEquals("Seed Lot", orchardPrd.get().getLotTypeDescription());
    Assertions.assertEquals("PRD", orchardPrd.get().getStageCode());

    Optional<Orchard> orchardEsb = orchardRepository.findById("820");

    Assertions.assertTrue(orchardEsb.isPresent());
    Assertions.assertEquals("FERNDALE INSTITUTE", orchardEsb.get().getName());
    Assertions.assertEquals("AX", orchardEsb.get().getVegetationCode());
    Assertions.assertEquals('C', orchardEsb.get().getLotTypeCode());
    Assertions.assertEquals("Cutting Lot", orchardEsb.get().getLotTypeDescription());
    Assertions.assertEquals("ESB", orchardEsb.get().getStageCode());
  }

  @Test
  @DisplayName("findByIdNotFoundTest")
  void findByIdNotFoundTest() {
    Optional<Orchard> orchardRet = orchardRepository.findById("612");

    Assertions.assertTrue(orchardRet.isEmpty());
  }
}
