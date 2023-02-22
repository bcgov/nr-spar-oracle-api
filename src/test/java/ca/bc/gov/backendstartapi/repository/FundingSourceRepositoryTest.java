package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.FundingSource;
import java.time.LocalDate;
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

  @Autowired private FundingSourceRepository fundingSourceRepository;

  private boolean isValid(FundingSource fundingSource) {
    LocalDate today = LocalDate.now();

    // Effective date - Should be before or same as today
    if (fundingSource.getEffectiveDate().isAfter(today)) {
      return false;
    }

    // Expiry date - Should be after today
    return fundingSource.getExpiryDate().isAfter(today);
  }

  @Test
  @DisplayName("findAllTest")
  @Sql(scripts = {"classpath:scripts/FundingSourceRepositoryTest_findAllTest.sql"})
  void findAllTest() {
    List<FundingSource> sources = fundingSourceRepository.findAllValid();

    Assertions.assertFalse(sources.isEmpty());
    Assertions.assertEquals(3, sources.size());

    FundingSource fundingSourceBct = sources.get(0);
    Assertions.assertEquals("BCT", fundingSourceBct.getCode());
    Assertions.assertEquals("BC Timber Sales", fundingSourceBct.getDescription());
    Assertions.assertTrue(isValid(fundingSourceBct));

    FundingSource fundingSourceCbi = sources.get(1);
    Assertions.assertEquals("CBI", fundingSourceCbi.getCode());
    Assertions.assertEquals("Carbon Offset Investment", fundingSourceCbi.getDescription());
    Assertions.assertTrue(isValid(fundingSourceCbi));

    FundingSource fundingSourceCl = sources.get(2);
    Assertions.assertEquals("CL", fundingSourceCl.getCode());
    Assertions.assertEquals("Catastrophic Losses", fundingSourceCl.getDescription());
    Assertions.assertTrue(isValid(fundingSourceCl));
  }
}
