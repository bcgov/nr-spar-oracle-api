package ca.bc.gov.backendstartapi.service;

import ca.bc.gov.backendstartapi.dto.OrchardLotTypeDescriptionDto;
import ca.bc.gov.backendstartapi.entity.Orchard;
import ca.bc.gov.backendstartapi.entity.OrchardLotTypeCode;
import ca.bc.gov.backendstartapi.repository.OrchardRepository;
import java.util.Objects;
import java.util.Optional;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** This class contains methods to handle orchards. */
@Setter
@NoArgsConstructor
@Service
@Slf4j
public class OrchardService {

  private OrchardRepository orchardRepository;

  @Autowired
  public OrchardService(OrchardRepository orchardRepository) {
    this.orchardRepository = orchardRepository;
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

      OrchardLotTypeDescriptionDto descriptionDto = new OrchardLotTypeDescriptionDto(
          orchard.get().getId(),
          orchard.get().getName(),
          orchard.get().getVegetationCode(),
          orchardLotTypeCode.getCode(),
          orchardLotTypeCode.getDescription(),
          orchard.get().getStageCode()
      );
      return Optional.of(descriptionDto);
    }

    return Optional.empty();
  }
}
