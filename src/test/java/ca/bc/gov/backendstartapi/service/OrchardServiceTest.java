package ca.bc.gov.backendstartapi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.bc.gov.backendstartapi.dto.OrchardLotTypeDescriptionDto;
import ca.bc.gov.backendstartapi.entity.Orchard;
import ca.bc.gov.backendstartapi.entity.OrchardLotTypeCode;
import ca.bc.gov.backendstartapi.repository.OrchardRepository;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

@ExtendWith(MockitoExtension.class)
class OrchardServiceTest {

  @Mock private OrchardRepository orchardRepository;

  @Autowired @InjectMocks private OrchardService orchardService;

  @Test
  @DisplayName("findNotRetiredOrchardValidLotTypeTest_Prd")
  void findNotRetiredOrchardValidLotTypeTest_Prd() {
    Orchard orchard = new Orchard();
    orchard.setId("337");
    orchard.setName("GRANDVIEW");
    orchard.setVegetationCode("PLI");
    orchard.setStageCode("PRD");

    LocalDate now = LocalDate.now();

    OrchardLotTypeCode lotTypeCode = new OrchardLotTypeCode();
    lotTypeCode.setCode('S');
    lotTypeCode.setDescription("Seed Lot");
    lotTypeCode.setEffectiveDate(now.minusDays(1L));
    lotTypeCode.setExpiryDate(now.plusDays(30L));
    lotTypeCode.setUpdateTimestamp(now);
    orchard.setOrchardLotTypeCode(lotTypeCode);

    when(orchardRepository.findNotRetiredById(any())).thenReturn(Optional.of(orchard));

    Optional<OrchardLotTypeDescriptionDto> descriptionDto =
        orchardService.findNotRetiredOrchardValidLotType("337");

    Assertions.assertTrue(descriptionDto.isPresent());
    Assertions.assertEquals("337", descriptionDto.get().id());
    Assertions.assertEquals("GRANDVIEW", descriptionDto.get().name());
    Assertions.assertEquals("PLI", descriptionDto.get().vegetationCode());
    Assertions.assertEquals("PRD", descriptionDto.get().stageCode());
    Assertions.assertEquals('S', descriptionDto.get().lotTypeCode());
    Assertions.assertEquals("Seed Lot", descriptionDto.get().lotTypeDescription());
  }

  @Test
  @DisplayName("findNotRetiredOrchardValidLotTypeTest_Esb")
  void findNotRetiredOrchardValidLotTypeTest_Esb() {
    Orchard orchard = new Orchard();
    orchard.setId("820");
    orchard.setName("FERNDALE INSTITUTE");
    orchard.setVegetationCode("AX");
    orchard.setStageCode("ESB");

    LocalDate now = LocalDate.now();

    OrchardLotTypeCode lotTypeCode = new OrchardLotTypeCode();
    lotTypeCode.setCode('C');
    lotTypeCode.setDescription("Cutting Lot");
    lotTypeCode.setEffectiveDate(now.minusDays(1L));
    lotTypeCode.setExpiryDate(now.plusDays(30L));
    lotTypeCode.setUpdateTimestamp(now);
    orchard.setOrchardLotTypeCode(lotTypeCode);

    when(orchardRepository.findNotRetiredById(any())).thenReturn(Optional.of(orchard));

    Optional<OrchardLotTypeDescriptionDto> descriptionDto =
        orchardService.findNotRetiredOrchardValidLotType("820");

    Assertions.assertTrue(descriptionDto.isPresent());
    Assertions.assertEquals("820", descriptionDto.get().id());
    Assertions.assertEquals("FERNDALE INSTITUTE", descriptionDto.get().name());
    Assertions.assertEquals("AX", descriptionDto.get().vegetationCode());
    Assertions.assertEquals("ESB", descriptionDto.get().stageCode());
    Assertions.assertEquals('C', descriptionDto.get().lotTypeCode());
    Assertions.assertEquals("Cutting Lot", descriptionDto.get().lotTypeDescription());
  }

  @Test
  @DisplayName("findNotRetiredOrchardValidLotTypeTest_Ret")
  void findNotRetiredOrchardValidLotTypeTest_Ret() {
    Orchard orchard = new Orchard();
    orchard.setId("612");
    orchard.setName("E.KOOTENAY BREED A");
    orchard.setVegetationCode("SX");
    orchard.setStageCode("RET");

    LocalDate now = LocalDate.now();

    OrchardLotTypeCode lotTypeCode = new OrchardLotTypeCode();
    lotTypeCode.setCode('S');
    lotTypeCode.setDescription("Seed Lot");
    lotTypeCode.setEffectiveDate(now.minusDays(3L));
    lotTypeCode.setExpiryDate(now.minusDays(1L)); // expired
    lotTypeCode.setUpdateTimestamp(now);
    orchard.setOrchardLotTypeCode(lotTypeCode);

    when(orchardRepository.findNotRetiredById(any())).thenReturn(Optional.of(orchard));

    Optional<OrchardLotTypeDescriptionDto> descriptionDto =
        orchardService.findNotRetiredOrchardValidLotType("612");

    Assertions.assertTrue(descriptionDto.isEmpty());
  }
}
