package ca.bc.gov.backendstartapi.service;

import ca.bc.gov.backendstartapi.dto.OrchardLotTypeDescriptionDto;
import ca.bc.gov.backendstartapi.dto.OrchardParentTreeDto;
import ca.bc.gov.backendstartapi.dto.ParentTreeDto;
import ca.bc.gov.backendstartapi.dto.ParentTreeGeneticQualityDto;
import ca.bc.gov.backendstartapi.entity.Orchard;
import ca.bc.gov.backendstartapi.entity.OrchardLotTypeCode;
import ca.bc.gov.backendstartapi.entity.ParentTree;
import ca.bc.gov.backendstartapi.entity.ParentTreeGeneticQuality;
import ca.bc.gov.backendstartapi.entity.ParentTreeOrchard;
import ca.bc.gov.backendstartapi.repository.OrchardRepository;
import ca.bc.gov.backendstartapi.repository.ParentTreeGeneticQualityRepository;
import ca.bc.gov.backendstartapi.repository.ParentTreeOrchardRepository;
import ca.bc.gov.backendstartapi.repository.ParentTreeRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** This class contains methods to handle orchards. */
@Service
@Slf4j
public class OrchardService {

  private OrchardRepository orchardRepository;

  private ParentTreeOrchardRepository parentTreeOrchardRepository;

  private ParentTreeRepository parentTreeRepository;

  private ParentTreeGeneticQualityRepository parentTreeGeneticQualityRepository;

  private OrchardService() {}

  @Autowired
  OrchardService(
      OrchardRepository orchardRepository,
      ParentTreeOrchardRepository parentTreeOrchardRepository,
      ParentTreeRepository parentTreeRepository,
      ParentTreeGeneticQualityRepository parentTreeGeneticQualityRepository) {
    this.orchardRepository = orchardRepository;
    this.parentTreeOrchardRepository = parentTreeOrchardRepository;
    this.parentTreeRepository = parentTreeRepository;
    this.parentTreeGeneticQualityRepository = parentTreeGeneticQualityRepository;
  }

  /**
   * Find a not retired {@link Orchard} with a valid {@link OrchardLotTypeCode} by Orchard's ID.
   *
   * @param id The Orchard's identification
   * @return Optional of {@link OrchardLotTypeDescriptionDto}
   */
  public Optional<OrchardLotTypeDescriptionDto> findNotRetiredOrchardValidLotType(String id) {
    log.info("Find valid not retired Orchard by id: {}", id);
    Optional<Orchard> orchard = orchardRepository.findNotRetiredById(id);

    if (orchard.isPresent()) {
      OrchardLotTypeCode orchardLotTypeCode = orchard.get().getOrchardLotTypeCode();
      if (Objects.isNull(orchardLotTypeCode) || !orchardLotTypeCode.isValid()) {
        log.info("Orchard lot type is not valid!");
        return Optional.empty();
      }

      OrchardLotTypeDescriptionDto descriptionDto =
          new OrchardLotTypeDescriptionDto(
              orchard.get().getId(),
              orchard.get().getName(),
              orchard.get().getVegetationCode(),
              orchardLotTypeCode.getCode(),
              orchardLotTypeCode.getDescription(),
              orchard.get().getStageCode());
      return Optional.of(descriptionDto);
    }

    return Optional.empty();
  }

  /**
   * Finds an Orchard parent tree contribution data given an orchard id and an SPU ID.
   *
   * @param orchardId {@link Orchard} identification
   * @param spuId SPU identification
   * @return Optional of {@link OrchardParentTreeDto}
   */
  public Optional<OrchardParentTreeDto> findParentTreeGeneticQualityData(
      String orchardId, Long spuId) {

    long starting = Instant.now().toEpochMilli();
    Optional<Orchard> orchard = orchardRepository.findById(orchardId);
    long endingOne = Instant.now().toEpochMilli();
    log.info("Time elapsed querying orchard by id: {}", endingOne - starting);

    if (orchard.isEmpty()) {
      return Optional.empty();
    }

    // Orchard
    OrchardParentTreeDto orchardParentTreeDto = new OrchardParentTreeDto();
    orchardParentTreeDto.setOrchardId(orchard.get().getId());
    orchardParentTreeDto.setVegetationCode(orchard.get().getVegetationCode());
    orchardParentTreeDto.setSeedPlanUnitId(spuId);

    long endingTwo = Instant.now().toEpochMilli();
    log.info("Time elapsed creating basic OrchardParentTreeDto: {}", endingTwo - endingOne);

    // Orchard x Parent Tree
    orchardParentTreeDto.setParentTrees(findAllParentTree(orchard.get().getId(), spuId, endingTwo));

    long ending = Instant.now().toEpochMilli();
    log.info("Time elapsed final: {}", ending - starting);
    return Optional.of(orchardParentTreeDto);
  }

  private List<ParentTreeDto> findAllParentTree(String orchardId, Long spuId, long milli) {
    List<ParentTreeDto> parentTreeDtoList = new ArrayList<>();
    List<ParentTreeOrchard> parentTreeOrchards =
        parentTreeOrchardRepository.findAllByOrchardId(orchardId);
    long endingThree = Instant.now().toEpochMilli();
    log.info("Time elapsed querying all parent tree to the orchard: {}", endingThree - milli);

    List<Long> orchardIdList =
        parentTreeOrchards.stream().map(ParentTreeOrchard::getParentTreeId).toList();

    long endingFour = Instant.now().toEpochMilli();
    log.info("Time elapsed mapping all parent tree orchard ids: {}", endingFour - endingThree);

    List<ParentTree> parentTreeList = parentTreeRepository.findAllIn(orchardIdList);

    long endingFive = Instant.now().toEpochMilli();
    log.info("Time elapsed finding all parent tree (select in): {}", endingFive - endingFour);

    List<Long> parentTreeIdList = parentTreeList.stream().map(ParentTree::getId).toList();
    long endingSix = Instant.now().toEpochMilli();
    log.info("Time elapsed mapping all parent tree ids: {}", endingSix - endingFive);

    List<ParentTreeGeneticQualityDto> qualityDtoList =
        findAllParentTreeGeneticQualities(spuId, parentTreeIdList);
    long endingSeven = Instant.now().toEpochMilli();
    log.info("Time elapsed querying all parent tree genetic quality: {}", endingSeven - endingSix);

    for (ParentTree parentTree : parentTreeList) {
      ParentTreeDto parentTreeDto = new ParentTreeDto();
      parentTreeDto.setParentTreeId(parentTree.getId());
      parentTreeDto.setParentTreeNumber(parentTree.getParentTreeNumber());
      parentTreeDto.setParentTreeRegStatusCode(parentTree.getParentTreeRegStatusCode());
      parentTreeDto.setLocalNumber(parentTree.getLocalNumber());
      parentTreeDto.setActive(parentTree.getActive());
      parentTreeDto.setTested(parentTree.getTested());
      parentTreeDto.setBreedingProgram(parentTree.getBreedingProgram());
      parentTreeDto.setFemaleParentTreeId(parentTree.getFemaleParentParentTreeId());
      parentTreeDto.setMaleParentTreeId(parentTree.getMaleParentParentTreeId());

      // remove from for
      parentTreeDto.setParentTreeGeneticQualities(
          qualityDtoList.stream()
              .filter(x -> x.getParentTreeId().equals(parentTree.getId()))
              .toList());

      parentTreeDtoList.add(parentTreeDto);
    }

    long endingEight = Instant.now().toEpochMilli();
    log.info("Time elapsed creating ParentTreeDto list: {}", endingEight - endingSeven);
    return parentTreeDtoList;
  }

  private List<ParentTreeGeneticQualityDto> findAllParentTreeGeneticQualities(
      Long spuId, List<Long> parentTreeIdList) {
    List<ParentTreeGeneticQualityDto> list = new ArrayList<>();

    boolean geneticWorthCalcInd = true;
    String geneticType = "BV";

    List<ParentTreeGeneticQuality> ptgqList =
        parentTreeGeneticQualityRepository.findAllBySpuGeneticWorthTypeParentTreeId(
            spuId, geneticWorthCalcInd, geneticType, parentTreeIdList);

    for (ParentTreeGeneticQuality parentTreeGen : ptgqList) {
      ParentTreeGeneticQualityDto geneticQualityDto = new ParentTreeGeneticQualityDto();
      geneticQualityDto.setParentTreeId(parentTreeGen.getParentTreeId());
      geneticQualityDto.setGeneticTypeCode(parentTreeGen.getGeneticTypeCode());
      geneticQualityDto.setGeneticWorthCode(parentTreeGen.getGeneticWorthCode());
      geneticQualityDto.setGeneticQualityValue(parentTreeGen.getGeneticQualityValue());

      list.add(geneticQualityDto);
    }
    return list;
  }
}
