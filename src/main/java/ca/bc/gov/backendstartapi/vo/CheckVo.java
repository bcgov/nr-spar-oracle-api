package ca.bc.gov.backendstartapi.vo;

import ca.bc.gov.backendstartapi.response.BaseResponse;

/** This class represents a check object. */
public record CheckVo(String message, String release) implements BaseResponse {}
