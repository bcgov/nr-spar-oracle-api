package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.Orchard;
import ca.bc.gov.backendstartapi.entity.OrchardLotTypeCode;
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
    Optional<Orchard> orchardPrd = orchardRepository.findNotRetiredById("337");

    Assertions.assertTrue(orchardPrd.isPresent());
    Orchard orchardOne = orchardPrd.get();
    OrchardLotTypeCode orchardLotTypeCodeOne = orchardOne.getOrchardLotTypeCode();

    Assertions.assertNotNull(orchardLotTypeCodeOne);
    Assertions.assertEquals("GRANDVIEW", orchardOne.getName());
    Assertions.assertEquals("PLI", orchardOne.getVegetationCode());
    Assertions.assertEquals('S', orchardLotTypeCodeOne.getCode());
    Assertions.assertEquals("Seed Lot", orchardLotTypeCodeOne.getDescription());
    Assertions.assertEquals("PRD", orchardOne.getStageCode());

    Optional<Orchard> orchardEsb = orchardRepository.findNotRetiredById("820");

    Assertions.assertTrue(orchardEsb.isPresent());
    Orchard orchardTwo = orchardEsb.get();
    OrchardLotTypeCode orchardLotTypeCodeTwo = orchardTwo.getOrchardLotTypeCode();

    Assertions.assertNotNull(orchardLotTypeCodeTwo);
    Assertions.assertEquals("FERNDALE INSTITUTE", orchardTwo.getName());
    Assertions.assertEquals("AX", orchardTwo.getVegetationCode());
    Assertions.assertEquals('C', orchardLotTypeCodeTwo.getCode());
    Assertions.assertEquals("Cutting Lot", orchardLotTypeCodeTwo.getDescription());
    Assertions.assertEquals("ESB", orchardTwo.getStageCode());
  }

  @Test
  @DisplayName("findByIdNotFoundTest")
  void findByIdNotFoundTest() {
    Optional<Orchard> orchardRet = orchardRepository.findNotRetiredById("612");

    Assertions.assertTrue(orchardRet.isEmpty());
  }
}
