-- DELETES
DELETE FROM CLIMATE_LOG;
ALTER TABLE CLIMATE_LOG ALTER COLUMN CLIMATE_LOG_ID RESTART WITH 1;
