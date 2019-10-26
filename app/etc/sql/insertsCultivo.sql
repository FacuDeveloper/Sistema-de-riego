-- DELETE
DELETE FROM CULTIVO;
ALTER TABLE CULTIVO ALTER COLUMN CULTIVO_ID RESTART WITH 1;

-- INSERTS
INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.40, 0.95, 0.90, 'ALFALFA', 150, 30, 150, 31, 0.55);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.50, 1.00, 0.95, 'ALCACHOFA', 40, 40, 250, 30, 0.45);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (1.60, 1.20, 1.10, 'BANANA', 60, 60, 75, 45, 0.50);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.30, 1.15, 0.25, 'CEBADA', 15, 25, 50, 30, 0.55);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.45, 0.45, 0.60, 'FRIJOLES SECOS', 20, 30, 40, 20, 0.50);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.50, 1.05, 0.90, 'JUDIAS VERDES', 20, 30, 30, 10, 0.50);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.70, 1.05, 0.95, 'CRUCIFIJOS DE COL', 40, 60, 50, 15, 0.45);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.35, 1.20, 0.60, 'ALGODON', 30, 50, 60, 55, 0.65);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.90, 0.95, 0.95, 'PALMERAS DATILERAS', 140, 30, 150, 45, 0.50);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.30, 1.10, 0.30, 'GRANOS PEQUENIOS', 25, 35, 65, 40, 0.55);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.30, 0.85, 0.45, 'UVAS DE MESA', 150, 50, 125, 40, 0.35);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.30, 0.70, 0.45, 'UVAS DE VINO', 150, 50, 125, 40, 0.45);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.90, 0.95, 0.95, 'CESPED FRESCO', 150, 40, 130, 45, 0.40);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.80, 0.85, 0.85, 'CESPED CALIENTE', 150, 40, 130, 45, 0.50);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.40, 1.15, 0.60, 'MANI', 25, 35, 45, 25, 0.45);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.30, 1.20, 0.35, 'MAIZ EN GRANO', 20, 35, 40, 30, 0.55);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.90, 1.10, 0.90, 'MANGO', 90, 90, 90, 45, 0.60);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.30, 1.00, 0.30, 'MIJO', 15, 25, 40, 25, 0.65);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.85, 0.95, 0.85, 'PASTO PARENE', 140, 60, 120, 45, 0.50);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.60, 1.05, 0.90, 'PIMIENTOS DULCES', 30, 35, 40, 20, 0.40);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.50, 1.15, 0.75, 'PATATA', 25, 30, 45, 30, 0.40);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.40, 1.15, 0.35, 'MAIZ', 20, 30, 40, 20, 0.60);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.30, 1.00, 0.55, 'SORGO GRANO', 20, 35, 40, 30, 0.65);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.40, 1.15, 0.50, 'HABA DE SOJA', 15, 15, 40, 15, 0.60);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.35, 1.20, 0.70, 'REMOLACHA DULCE', 25, 35, 50, 50, 0.65);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.40, 1.25, 0.75, 'CAÑA DE AZUCAR', 30, 60, 180, 95, 0.65);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.35, 1.15, 0.35, 'GIRASOL', 25, 35, 45, 25, 0.55);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.50, 1.05, 0.75, 'MELON DULCE', 25, 35, 40, 20, 0.42);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.50, 1.15, 0.80, 'TABACO', 20, 30, 30, 30, 0.55);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.60, 1.15, 0.80, 'TOMATE', 30, 40, 45, 30, 0.45);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.70, 1.05, 0.95, 'VEGETALES PEQUENIOS', 20, 30, 30, 15, 0.40);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.30, 1.15, 0.30, 'TRIGO PRIMAVERA', 30, 30, 40, 30, 0.60);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.70, 1.15, 0.25, 'TRIGO INVIERNO FF', 30, 140, 40, 30, 0.65);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.40, 1.15, 0.25, 'TRIGO INVIERNO', 160, 75, 75, 25, 0.65);

INSERT INTO CULTIVO(KC_INICIAL, KC_MEDIO, KC_FINAL, NOMBRE, E_INICIAL, E_DESARROLLO, E_MEDIA, E_FINAL, AGOTAMIENTO_CRITICO)
VALUES (0.70, 0.65, 0.70, 'CRUCIFICOS DE COL', 60, 90, 120, 95, 0.50);
