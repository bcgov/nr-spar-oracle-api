package ca.bc.gov.backendstartapi.vo;

import ca.bc.gov.backendstartapi.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CheckVO implements BaseResponse {

  private String message;
}
