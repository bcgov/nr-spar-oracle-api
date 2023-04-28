package ca.bc.gov.backendstartapi.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import ca.bc.gov.backendstartapi.dto.OrchardLotTypeDescriptionDto;
import ca.bc.gov.backendstartapi.dto.OrchardParentTreeDto;
import ca.bc.gov.backendstartapi.dto.ParentTreeDto;
import ca.bc.gov.backendstartapi.dto.ParentTreeGeneticQualityDto;
import ca.bc.gov.backendstartapi.entity.Orchard;
import ca.bc.gov.backendstartapi.entity.OrchardLotTypeCode;
import ca.bc.gov.backendstartapi.entity.ParentTree;
import ca.bc.gov.backendstartapi.entity.ParentTreeGeneticQuality;
import ca.bc.gov.backendstartapi.entity.ParentTreeOrchard;
import ca.bc.gov.backendstartapi.entity.ParentTreeOrchardId;
import ca.bc.gov.backendstartapi.repository.OrchardRepository;
import ca.bc.gov.backendstartapi.repository.ParentTreeGeneticQualityRepository;
import ca.bc.gov.backendstartapi.repository.ParentTreeOrchardRepository;
import ca.bc.gov.backendstartapi.repository.ParentTreeRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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

  @Mock private ParentTreeOrchardRepository parentTreeOrchardRepository;

  @Mock private ParentTreeRepository parentTreeRepository;

  @Mock private ParentTreeGeneticQualityRepository parentTreeGeneticQualityRepository;

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

  @Test
  @DisplayName("findParentTreeGeneticQualityDataTest_Empty")
  void findParentTreeGeneticQualityDataTest_Empty() {
    String orchardId = "999";
    when(orchardRepository.findById(orchardId)).thenReturn(Optional.empty());

    Optional<OrchardParentTreeDto> dto =
        orchardService.findParentTreeGeneticQualityData(orchardId, 2L);

    Assertions.assertTrue(dto.isEmpty());
  }

  @Test
  @DisplayName("findParentTreeGeneticQualityDataTest_Success")
  void findParentTreeGeneticQualityDataTest_Success() {
    String orchardId = "407";

    // Orchard
    Orchard orchard = new Orchard();
    orchard.setId(orchardId);
    orchard.setName("Test");
    orchard.setVegetationCode("FDC");
    orchard.setStageCode("ABC");

    when(orchardRepository.findById(orchardId)).thenReturn(Optional.of(orchard));

    // Parent Tree Orchard
    ParentTreeOrchard parentTreeOrchard1 = new ParentTreeOrchard();
    ParentTreeOrchardId parentTreeOrchardId1 = new ParentTreeOrchardId();
    parentTreeOrchardId1.setParentTreeId(4032L);
    parentTreeOrchardId1.setOrchardId(orchardId);
    parentTreeOrchard1.setId(parentTreeOrchardId1);

    when(parentTreeOrchardRepository.findByIdOrchardId(orchardId))
        .thenReturn(List.of(parentTreeOrchard1));

    // Parent Tree
    ParentTree parentTree = new ParentTree();
    parentTree.setId(4032L);
    parentTree.setParentTreeNumber("37");
    parentTree.setVegetationCode("FDC");
    parentTree.setParentTreeRegStatusCode("APP");
    parentTree.setLocalNumber("123");
    parentTree.setActive(true);
    parentTree.setTested(true);
    parentTree.setBreedingProgram(true);

    when(parentTreeRepository.findAllIn(any())).thenReturn(List.of(parentTree));

    Long spuId = 7L;

    // Parent Tree Genetic Quality
    ParentTreeGeneticQuality geneticQuality = new ParentTreeGeneticQuality();
    geneticQuality.setId(555L);
    geneticQuality.setParentTreeId(parentTreeOrchard1.getId().getParentTreeId());
    geneticQuality.setSeedPlanningUnitId(spuId);
    geneticQuality.setGeneticTypeCode("BV");
    geneticQuality.setGeneticWorthCode("GVO");
    geneticQuality.setGeneticQualityValue(new BigDecimal("18.0"));
    geneticQuality.setToBeUsedInCalculations(true);

    when(parentTreeGeneticQualityRepository.findAllBySpuGeneticWorthTypeParentTreeId(
            spuId, true, "BV", List.of(4032L)))
        .thenReturn(List.of(geneticQuality));

    Optional<OrchardParentTreeDto> dto =
        orchardService.findParentTreeGeneticQualityData(orchardId, spuId);

    Assertions.assertFalse(dto.isEmpty());

    OrchardParentTreeDto orchardParentTreeDto = dto.get();

    // Orchard Parent Tree
    Assertions.assertEquals(orchardId, orchardParentTreeDto.getOrchardId());
    Assertions.assertEquals("FDC", orchardParentTreeDto.getVegetationCode());
    Assertions.assertEquals(7L, orchardParentTreeDto.getSeedPlanningUnitId());
    Assertions.assertEquals(1, orchardParentTreeDto.getParentTrees().size());

    // Parent Trees
    ParentTreeDto parentTreeDto = orchardParentTreeDto.getParentTrees().get(0);
    Assertions.assertEquals(4032L, parentTreeDto.getParentTreeId());
    Assertions.assertEquals("37", parentTreeDto.getParentTreeNumber());
    Assertions.assertEquals("APP", parentTreeDto.getParentTreeRegStatusCode());
    Assertions.assertEquals("123", parentTreeDto.getLocalNumber());
    Assertions.assertTrue(parentTreeDto.isActive());
    Assertions.assertTrue(parentTreeDto.isTested());
    Assertions.assertTrue(parentTreeDto.isBreedingProgram());
    Assertions.assertNull(parentTreeDto.getFemaleParentTreeId());
    Assertions.assertNull(parentTreeDto.getMaleParentTreeId());
    Assertions.assertEquals(1, parentTreeDto.getParentTreeGeneticQualities().size());

    // Parent Tree Genetic Quality
    ParentTreeGeneticQualityDto geneticDto = parentTreeDto.getParentTreeGeneticQualities().get(0);
    Assertions.assertEquals(4032L, geneticDto.getParentTreeId());
    Assertions.assertEquals("BV", geneticDto.getGeneticTypeCode());
    Assertions.assertEquals("GVO", geneticDto.getGeneticWorthCode());
    Assertions.assertEquals(new BigDecimal("18.0"), geneticDto.getGeneticQualityValue());
  }
}
