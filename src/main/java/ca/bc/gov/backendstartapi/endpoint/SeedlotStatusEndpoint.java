package ca.bc.gov.backendstartapi.endpoint;

import ca.bc.gov.backendstartapi.enumeration.SeedlotStatus;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Endpoints to fetch {@link SeedlotStatus}. */
@RestController
@RequestMapping(path = "/api/seedlot-status", produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
public class SeedlotStatusEndpoint extends DescribedEnumEndpoint<SeedlotStatus> {

  SeedlotStatusEndpoint() {
    enumClass = SeedlotStatus.class;
  }
}
