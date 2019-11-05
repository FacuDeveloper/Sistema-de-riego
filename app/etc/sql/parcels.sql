-- DELETE
DELETE FROM PARCEL;
ALTER TABLE PARCEL ALTER COLUMN PARCEL_ID RESTART WITH 1;

-- INSERTS

-- Parcela con coordenadas geograficas de Puerto Madryn
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME) VALUES (200, -42.7683337, -65.060855, 'Parcela PM1');

-- Parcela con coordenadas geograficas de Sierra Grande
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME) VALUES (250, -41.6098881, -65.3664475, 'Parcela SG1');

-- Parcela con coordenadas geograficas de CABA
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME) VALUES (300, -34.6156625, -58.5033379, 'Parcela CABA1');

-- Parcela con coordenadas geograficas de Viedma
INSERT INTO PARCEL (AREA, LATITUDE, LONGITUDE, PARCEL_NAME) VALUES (350, -40.8249902, -63.0176492, 'Parcela V1');
