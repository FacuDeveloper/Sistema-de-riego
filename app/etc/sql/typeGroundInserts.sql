-- INSERTS
DELETE FROM GROUND;
ALTER TABLE GROUND ALTER COLUMN GROUND_ID RESTART WITH 1;

DELETE FROM TYPEGROUND;
ALTER TABLE TYPEGROUND ALTER COLUMN TYP_GRO_ID RESTART WITH 1;

-- INSERTS
INSERT INTO TYPEGROUND (TEXTURE, DESCRIPTION) VALUES ('Arenoso', 'Contiene partículas más grandes que el resto de suelos. Es áspero y seco al tacto y el agua se drena rapidamente.');
INSERT INTO TYPEGROUND (TEXTURE, DESCRIPTION) VALUES ('Limoso', 'Tiene partículas más pequeñas y suaves al tacto que los arenosos. Retienen el agua por más tiempo.');
INSERT INTO TYPEGROUND (TEXTURE, DESCRIPTION) VALUES ('Arcilloso', 'Está formado por granos finos de color amarillento. Retiene mucho el agua y forma charcos');
