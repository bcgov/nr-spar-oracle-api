package ca.bc.gov.backendstartapi.implementation.repository;

import ca.bc.gov.backendstartapi.implementation.VegetationCodeImpl;
import ca.bc.gov.backendstartapi.implementation.entity.VegetationCodeEntity;
import ca.bc.gov.backendstartapi.model.VegetationCode;
import ca.bc.gov.backendstartapi.model.repository.VegetationCodeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link VegetationCodeRepository} that works with {@link VegetationCodeImpl}.
 */
@Component
public class VegetationCodeRepositoryImpl implements VegetationCodeRepository {

  @PersistenceContext private EntityManager em;
  @Autowired private VegetationCodeJpaRepository jpaRepository;

  @Override
  public Optional<VegetationCode> findByCode(String code) {
    return jpaRepository.findById(code).map(VegetationCodeImpl::of);
  }

  @Override
  public List<VegetationCode> findValidByCodeOrDescription(
      String search, int skip, int maxResults) {
    var query =
        em.createNamedQuery(
                VegetationCodeEntity.FIND_VALID_BY_CODE_OR_DESCRIPTION, VegetationCodeEntity.class)
            .setParameter("search", "%" + search + "%")
            .setFirstResult(skip)
            .setMaxResults(maxResults);
    return query.getResultList().stream()
        .map(entity -> (VegetationCode) VegetationCodeImpl.of(entity))
        .toList();
  }
}
