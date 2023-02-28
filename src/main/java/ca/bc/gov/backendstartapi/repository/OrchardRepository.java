package ca.bc.gov.backendstartapi.repository;

import ca.bc.gov.backendstartapi.entity.Orchard;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/** This repository class contains methods to retrieve Orchards from the database. */
public interface OrchardRepository extends JpaRepository<Orchard, String> {

  @Query(
      value =
          """
          select O.ORCHARD_ID
            ,O.ORCHARD_NAME
            ,O.VEGETATION_CODE
            ,O.ORCHARD_LOT_TYPE_CODE
            ,T.DESCRIPTION AS ORCHARD_LOT_TYPE_DESCRIPTION
            ,O.ORCHARD_STAGE_CODE
          from ORCHARD O
          join ORCHARD_LOT_TYPE_CODE T ON (
            T.ORCHARD_LOT_TYPE_CODE = O.ORCHARD_LOT_TYPE_CODE
            AND CURRENT_DATE BETWEEN T.EFFECTIVE_DATE AND T.EXPIRY_DATE
          )
          where O.ORCHARD_STAGE_CODE != 'RET'
            and O.ORCHARD_ID = ?1
          """,
      nativeQuery = true)
  Optional<Orchard> findById(String id);
}
