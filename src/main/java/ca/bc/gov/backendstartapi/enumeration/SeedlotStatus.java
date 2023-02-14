package ca.bc.gov.backendstartapi.enumeration;

/** Possible status for Seed lots. */
public enum SeedlotStatus implements DescribedEnum {
  APP("Approved"),
  CAN("Cancelled"),
  COM("Complete"),
  INC("Incomplete"),
  PND("Pending"),
  EXP("Expired"),
  SUB("Submitted");

  private final String description;

  SeedlotStatus(String description) {
    this.description = description;
  }

  @Override
  public String description() {
    return description;
  }
}
