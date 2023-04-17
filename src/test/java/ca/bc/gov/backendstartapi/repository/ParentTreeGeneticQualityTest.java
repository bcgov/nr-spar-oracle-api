package ca.bc.gov.backendstartapi.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;

import ca.bc.gov.backendstartapi.entity.ParentTreeGeneticQuality;
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
public class ParentTreeGeneticQualityTest {

  @Autowired private ParentTreeGeneticQualityRepository parentTreeGeneticQualityRepository;

  @Test
  @DisplayName("findAllBySpuGeneticWorthTypeParentTreeIdTest")
  @Sql(scripts = {"classpath:scripts/ParentTreeGeneticQuality.sql"})
  void findAllBySpuGeneticWorthTypeParentTreeIdTest() {
    long spuId = 7L;
    char geneticWorthCalcInd = 'Y';
    String geneticType = "BV";
    List<Long> parentTreeIdList = List.of(4032L);

    List<ParentTreeGeneticQuality> parentTreeGeneticQualities =
        parentTreeGeneticQualityRepository.findAllBySpuGeneticWorthTypeParentTreeId(
            spuId, geneticWorthCalcInd, geneticType, parentTreeIdList);

    assertFalse(parentTreeGeneticQualities.isEmpty());

    // keep going from here
  }
}
