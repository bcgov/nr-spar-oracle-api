SET @today = current_date;
SET @next_year = DATEADD(YEAR, 1, @today);

insert into ORCHARD_LOT_TYPE_CODE
(ORCHARD_LOT_TYPE_CODE, DESCRIPTION, EFFECTIVE_DATE, EXPIRY_DATE, UPDATE_TIMESTAMP)
values
  ('C', 'Cutting Lot', @today, @next_year, current_date),
  ('S', 'Seed Lot', @today, @next_year, current_date);

-- ESB - Established
insert into ORCHARD (
  ORCHARD_ID,
  ORCHARD_NAME,
  VEGETATION_CODE,
  ORCHARD_LOT_TYPE_CODE,
  ORCHARD_STAGE_CODE
) VALUES (
  '820',
  'FERNDALE INSTITUTE',
  'AX',
  'C',
  'ESB'
);

-- RET - Retired
insert into ORCHARD (
  ORCHARD_ID,
  ORCHARD_NAME,
  VEGETATION_CODE,
  ORCHARD_LOT_TYPE_CODE,
  ORCHARD_STAGE_CODE
) VALUES (
  '612',
  'E.KOOTENAY BREED A',
  'SX',
  'S',
  'RET'
);

-- PRD - Producing
insert into ORCHARD (
  ORCHARD_ID,
  ORCHARD_NAME,
  VEGETATION_CODE,
  ORCHARD_LOT_TYPE_CODE,
  ORCHARD_STAGE_CODE
) VALUES (
  '337',
  'GRANDVIEW',
  'PLI',
  'S',
  'PRD'
);
