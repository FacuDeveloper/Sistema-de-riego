-- DELETE
DELETE FROM PARCEL;
ALTER TABLE PARCEL ALTER COLUMN PARCEL_ID RESTART WITH 1;

-- INSERTS

-- Parcela con coordenadas geograficas de Puerto Madryn
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME, ACTIVE) VALUES (1, -42.7683337, -65.060855, 'Parcela Puerto Madryn 1', 1);

-- Parcela con coordenadas geograficas de Sierra Grande
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME, ACTIVE) VALUES (2, -41.6098881, -65.3664475, 'Parcela Sierra Grande 1', 0);

-- Parcela con coordenadas geograficas de CABA
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME, ACTIVE) VALUES (3, -34.6156625, -58.5033379, 'Parcela CABA 1', 1);

-- Parcela con coordenadas geograficas de Viedma
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME, ACTIVE) VALUES (4, -40.8249902, -63.0176492, 'Parcela Viedma 1', 0);

-- Parcela con coordenadas geograficas de Cholila
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME, ACTIVE) VALUES (0.5, -42.5091569, -71.4351097, 'Parcela Cholila 1', 1);

-- Parcela con coordenadas geograficas de Esquel
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME, ACTIVE) VALUES (1.5, -42.9120944, -71.3396796, 'Parcela Esquel 1', 0);

-- Parcela con coordenadas geograficas de El Bolson
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME, ACTIVE) VALUES (3.5, -41.9657023, -71.5414559, 'Parcela El Bolson 1', 1);

-- Parcela con coordenadas geograficas de Neuquen
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME, ACTIVE) VALUES (5, -38.9411626, -68.1504201, 'Parcela Neuquén 1', 0);
