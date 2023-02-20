package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.FundingSource;
import java.util.List;
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
class FundingSourceRepositoryTest {

  @Autowired
  private FundingSourceRepository fundingSourceRepository;

  @Test
  @DisplayName("findAllTest")
  @Sql(scripts = {"classpath:scripts/FundingSourceRepositoryTest_findAllTest.sql"})
  void findAllTest() {
    List<FundingSource> sources = fundingSourceRepository.findAllValid();

    Assertions.assertFalse(sources.isEmpty());
    Assertions.assertEquals(3, sources.size());
  }
}
