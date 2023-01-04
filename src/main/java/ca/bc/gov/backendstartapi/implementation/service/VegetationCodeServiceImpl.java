package ca.bc.gov.backendstartapi.implementation.service;

import ca.bc.gov.backendstartapi.implementation.VegetationCodeImpl;
import ca.bc.gov.backendstartapi.model.VegetationCode;
import ca.bc.gov.backendstartapi.model.repository.VegetationCodeRepository;
import ca.bc.gov.backendstartapi.model.service.VegetationCodeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** Implementation of {@link VegetationCodeService} that works with {@link VegetationCodeImpl}. */
@Service
public class VegetationCodeServiceImpl implements VegetationCodeService {

  @Autowired private VegetationCodeRepository vegetationCodeRepository;

  @Override
  public Optional<VegetationCode> findByCode(String code) {
    return vegetationCodeRepository.findByCode(code);
  }

  @Override
  public List<VegetationCode> findValidByCodeOrDescription(String search, int page, int pageSize) {
    return vegetationCodeRepository.findValidByCodeOrDescription(search, page, pageSize);
  }
}
