-- DELETE
DELETE FROM INSTANCIA_PARCELA;
ALTER TABLE INSTANCIA_PARCELA ALTER COLUMN INSTANCIA_PARCELA_ID RESTART WITH 1;

-- INSERTS
INSERT INTO INSTANCIA_PARCELA (FECHA_SIEMBRA, FECHA_COSECHA, FK_PARCELA, FK_CULTIVO, FK_ESTADO) VALUES ('2018-11-16', '2019-11-12', 1, 1, 1);
