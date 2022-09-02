package ca.bc.gov.backendstartapi.vo;

import ca.bc.gov.backendstartapi.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** This class represents a check object. */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckVo implements BaseResponse {

  private String message;
  private String release;
}
