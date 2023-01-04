package ca.bc.gov.backendstartapi.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import ca.bc.gov.backendstartapi.implementation.entity.VegetationCodeEntity;
import ca.bc.gov.backendstartapi.model.VegetationCode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class VegetationCodeImplTest {

  @Test
  void shouldNotAcceptNullCode() {
    assertThrowsExactly(
        IllegalArgumentException.class,
        () -> new VegetationCodeImpl(null, "Pines", LocalDate.now(), LocalDate.now().plusYears(1)));
  }

  @Test
  void shouldNotAcceptNullDescription() {
    assertThrowsExactly(
        IllegalArgumentException.class,
        () -> new VegetationCodeImpl("AC", null, LocalDate.now(), LocalDate.now().plusYears(1)));
  }

  @Test
  void shouldNotAcceptNullEffectiveDate() {
    assertThrowsExactly(
        IllegalArgumentException.class,
        () -> new VegetationCodeImpl("AC", "Pines", null, LocalDate.now().plusYears(1)));
  }

  @Test
  void shouldNotAcceptNullExpiryDate() {
    assertThrowsExactly(
        IllegalArgumentException.class,
        () -> new VegetationCodeImpl("AC", "Pines", LocalDate.now(), null));
  }

  @Test
  void expiryDateMustBePosteriorToEffectiveDate() {
    assertThrowsExactly(
        IllegalArgumentException.class,
        () -> new VegetationCodeImpl("AC", "Pines", LocalDate.now(), LocalDate.now()));
  }

  @Test
  void createFromEntity_valid() {
    var entity = new VegetationCodeEntity();
    entity.setId("AC");
    entity.setDescription("Pines");
    entity.setEffectiveDate(LocalDate.now());
    entity.setExpiryDate(LocalDate.now().plusYears(1));
    entity.setUpdateTimestamp(LocalDateTime.now());
    VegetationCode vc = VegetationCodeImpl.of(entity);

    assertEquals(entity.getId(), vc.code());
    assertEquals(entity.getDescription(), vc.description());
    assertTrue(vc.isValid());
  }

  @Test
  void createFromEntity_invalid() {
    var entity = new VegetationCodeEntity();
    entity.setId("AC");
    entity.setDescription("Pines");
    entity.setEffectiveDate(LocalDate.now().minusYears(1));
    entity.setExpiryDate(LocalDate.now());
    entity.setUpdateTimestamp(LocalDateTime.now());
    VegetationCode vc = VegetationCodeImpl.of(entity);

    assertEquals(entity.getId(), vc.code());
    assertEquals(entity.getDescription(), vc.description());
    assertFalse(vc.isValid());
  }
}
