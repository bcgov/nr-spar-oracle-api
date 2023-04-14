package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.ParentTreeGeneticQuality;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ParentTreeGeneticQualityRepository
    extends JpaRepository<ParentTreeGeneticQuality, Long> {

  @Query(
      value =
          """
          from ParentTreeGeneticQuality
          where seedPlaningUnitId = ?1
            and geneticWorthCalcInd = ?2
            and geneticTypeCode = ?3
            and parentTreeId in ?4
          """)
  List<ParentTreeGeneticQuality> findAllBySpuGeneticWorthTypeParentTreeId(
      Long spuId, Character geneticWorth, String geneticType, List<Long> parentTreeId);
}
