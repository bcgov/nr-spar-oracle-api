package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.ParentTreeGeneticQuality;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * This class holds methods for retrieving {@link ParentTreeGeneticQuality} data from the database.
 */
public interface ParentTreeGeneticQualityRepository
    extends JpaRepository<ParentTreeGeneticQuality, Long> {

  @Query(
      value =
          """
          from ParentTreeGeneticQuality
          where seedPlaningUnitId = ?1
            and toBeUsedInCalculations = ?2
            and geneticTypeCode = ?3
            and parentTreeId in ?4
          """)
  List<ParentTreeGeneticQuality> findAllBySpuGeneticWorthTypeParentTreeId(
      Long spuId,
      boolean geneticWorthCalcInd,
      String geneticTypeCode,
      List<Long> parentTreeIdList);
}
