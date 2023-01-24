SET @today = current_date;
SET @last_year = DATEADD(YEAR, -1, @today);
SET @next_year = DATEADD(YEAR, 1, @today);

INSERT INTO vegetation_code
(vegetation_code, description, effective_date, expiry_date, update_timestamp)
VALUES ('VC1', 'Vegetation code 1', @today, @next_year, current_timestamp),
       ('VC2', 'Vegetation code 2', @last_year, @today, current_timestamp),
       ('VC3', 'Vegetation code 3', @today, @next_year, current_timestamp),
       ('VC4', 'Vegetation code 4', @today, @next_year, current_timestamp);
