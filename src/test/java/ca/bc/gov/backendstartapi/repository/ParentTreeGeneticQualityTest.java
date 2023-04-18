package ca.bc.gov.backendstartapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.bc.gov.backendstartapi.entity.ParentTreeGeneticQuality;
import java.math.BigDecimal;
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
class ParentTreeGeneticQualityTest {

  @Autowired private ParentTreeGeneticQualityRepository parentTreeGeneticQualityRepository;

  @Test
  @DisplayName("findAllBySpuGeneticWorthTypeParentTreeIdTest")
  @Sql(scripts = {"classpath:scripts/ParentTreeGeneticQuality.sql"})
  void findAllBySpuGeneticWorthTypeParentTreeIdTest() {
    long spuId = 7L;
    char geneticWorthCalcInd = 'Y';
    String geneticTypeCode = "BV";
    List<Long> parentTreeIdList = List.of(4032L);

    List<ParentTreeGeneticQuality> parentTreeGeneticQualities =
        parentTreeGeneticQualityRepository.findAllBySpuGeneticWorthTypeParentTreeId(
            spuId, geneticWorthCalcInd, geneticTypeCode, parentTreeIdList);

    assertFalse(parentTreeGeneticQualities.isEmpty());

    ParentTreeGeneticQuality geneticQuality = parentTreeGeneticQualities.get(0);

    assertEquals(2563L, geneticQuality.getId());
    assertEquals(4032L, geneticQuality.getParentTreeId());
    assertEquals(7L, geneticQuality.getSeedPlaningUnitId());
    assertEquals("BV", geneticQuality.getGeneticTypeCode());
    assertEquals("GVO", geneticQuality.getGeneticWorthCode());
    assertEquals(new BigDecimal("18.0"), geneticQuality.getGeneticQualityValue());
    assertEquals('Y', geneticQuality.getGeneticWorthCalcInd());
  }

  @Test
  @DisplayName("findAllBySpuGeneticWorthTypeParentTreeIdEmptyTest")
  void findAllBySpuGeneticWorthTypeParentTreeIdEmptyTest() {
    List<ParentTreeGeneticQuality> parentTreeGeneticQualities =
        parentTreeGeneticQualityRepository.findAllBySpuGeneticWorthTypeParentTreeId(
            0L, 'Y', "BV", List.of());

    assertTrue(parentTreeGeneticQualities.isEmpty());
  }
}
