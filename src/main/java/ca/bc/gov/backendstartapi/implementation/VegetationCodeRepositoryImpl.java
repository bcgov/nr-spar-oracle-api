package ca.bc.gov.backendstartapi.implementation;

import ca.bc.gov.backendstartapi.entity.VegetationCode;
import ca.bc.gov.backendstartapi.repository.VegetationCodeJpaRepository;
import ca.bc.gov.backendstartapi.repository.VegetationCodeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/** An implementation of {@link VegetationCodeRepository}. */
@Repository
public class VegetationCodeRepositoryImpl implements VegetationCodeRepository {

  @PersistenceContext private EntityManager em;
  @Autowired private VegetationCodeJpaRepository jpaRepository;

  @Override
  public Optional<VegetationCode> findByCode(String code) {
    return jpaRepository.findById(code);
  }

  @Override
  public List<VegetationCode> findValidByCodeOrDescription(
      String search, int skip, int maxResults) {
    var query =
        em.createNamedQuery(VegetationCode.FIND_VALID_BY_CODE_OR_DESCRIPTION, VegetationCode.class)
            .setParameter("search", "%" + search + "%")
            .setFirstResult(skip)
            .setMaxResults(maxResults);
    return List.copyOf(query.getResultList());
  }
}
