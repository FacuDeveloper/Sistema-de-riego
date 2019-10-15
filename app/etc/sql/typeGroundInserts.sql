-- DELETES
DELETE FROM GROUND;
ALTER TABLE GROUND ALTER COLUMN GROUND_ID RESTART WITH 1;

DELETE FROM TYPE_GROUND;
ALTER TABLE TYPE_GROUND ALTER COLUMN TYPE_GROUND_ID RESTART WITH 1;

-- INSERTS TYPE_GROUND
INSERT INTO TYPE_GROUND (TEXTURE, DESCRIPTION) VALUES ('Arenoso', 'Contiene partículas más grandes que el resto de suelos. Es áspero y seco al tacto y el agua se drena rapidamente.');
INSERT INTO TYPE_GROUND (TEXTURE, DESCRIPTION) VALUES ('Limoso', 'Tiene partículas más pequeñas y suaves al tacto que los arenosos. Retienen el agua por más tiempo.');
INSERT INTO TYPE_GROUND (TEXTURE, DESCRIPTION) VALUES ('Arcilloso', 'Está formado por granos finos de color amarillento. Retiene mucho el agua y forma charcos');
