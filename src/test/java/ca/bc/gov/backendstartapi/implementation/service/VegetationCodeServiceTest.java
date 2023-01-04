package ca.bc.gov.backendstartapi.implementation.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.bc.gov.backendstartapi.model.VegetationCode;
import ca.bc.gov.backendstartapi.model.service.VegetationCodeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Sql("/scripts/vegetationCodes.sql")
@Transactional
class VegetationCodeServiceTest {

  @Autowired private VegetationCodeService vegetationCodeService;

  @Test
  void findById_valid() {
    var vc1 = vegetationCodeService.findByCode("VC1");
    assertTrue(vc1.isPresent());

    var vc = vc1.get();
    assertEquals("VC1", vc.code());
    assertEquals("Vegetation code 1", vc.description());
    assertTrue(vc.isValid());
  }

  @Test
  void findById_invalid() {
    var vc2 = vegetationCodeService.findByCode("VC2");
    assertTrue(vc2.isPresent());

    var vc = vc2.get();
    assertEquals("VC2", vc.code());
    assertEquals("Vegetation code 2", vc.description());
    assertFalse(vc.isValid());
  }

  @Test
  void findById_inexistent() {
    var vc0 = vegetationCodeService.findByCode("VC0");
    assertTrue(vc0.isEmpty());
  }

  @Test
  void searchValid_match() {
    var results = vegetationCodeService.findValidByCodeOrDescription("vc", 0, 20);
    assertEquals(2, results.size());

    assertTrue(results.stream().allMatch(VegetationCode::isValid), "All results must be valid.");

    assertEquals(
        results.stream()
            .sorted((vc1, vc2) -> CharSequence.compare(vc1.code(), vc2.code()))
            .toList(),
        results,
        "Results must be sorted by code.");

    var vc1 = results.get(0);

    assertEquals("VC1", vc1.code());
    assertEquals("Vegetation code 1", vc1.description());

    var vc3 = results.get(1);

    assertEquals("VC3", vc3.code());
    assertEquals("Vegetation code 3", vc3.description());
  }

  @Test
  void searchValid_pagination() {
    var results = vegetationCodeService.findValidByCodeOrDescription("vc", 1, 1);
    assertEquals(1, results.size());

    assertTrue(results.stream().allMatch(VegetationCode::isValid), "All results must be valid.");

    var vc3 = results.get(0);

    assertEquals("VC3", vc3.code());
    assertEquals("Vegetation code 3", vc3.description());
  }

  @Test
  void searchValid_noMatch() {
    var results = vegetationCodeService.findValidByCodeOrDescription("not vc", 0, 20);
    assertEquals(0, results.size());
  }
}
