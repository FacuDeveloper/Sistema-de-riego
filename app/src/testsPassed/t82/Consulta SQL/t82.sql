-- Tarea 82 en el documento de tareas: Obtención del cultivo que más fue plantado en una parcela dada
-- Tarea 79 en GitHub: Crear función para obtener el cultivo que más fue plantado en una parcela dada

-- Filtra por le ID de una parcela dada, agrupa por el ID del cultivo y cuenta la cantidad de veces que se repite el ID de cultivo en cada grupo
-- Un detalle muy imporante es que se hace esta consulta sobre los registros de parcela (instancias de parcela) que estan en el estado "Finalizado", el cual
-- tiene ID = 1
SELECT FK_CULTIVO, COUNT(FK_CULTIVO) AS COUNT_CULTIVO
FROM INSTANCIA_PARCELA INNER JOIN INSTANCIA_PARCELA_ESTADO ON INSTANCIA_PARCELA.FK_ESTADO = INSTANCIA_PARCELA_ESTADO.INSTANCIA_PARCELA_ESTADO_ID
WHERE INSTANCIA_PARCELA.FK_PARCELA = 1 AND NOMBRE = 'Finalizado'
GROUP BY FK_CULTIVO;

-- Selecciona el maximo del conteo de cada cultivo plantado en una parcela dada, la cual se elige en base a su ID
-- Un detalle muy imporante es que se hace esta consulta sobre los registros de parcela (instancias de parcela) que estan en el estado "Finalizado"
SELECT MAX(COUNT_CULTIVO) AS MAX_CULTIVO
FROM (SELECT COUNT(FK_CULTIVO) AS COUNT_CULTIVO FROM INSTANCIA_PARCELA
INNER JOIN INSTANCIA_PARCELA_ESTADO ON INSTANCIA_PARCELA.FK_ESTADO = INSTANCIA_PARCELA_ESTADO.INSTANCIA_PARCELA_ESTADO_ID
WHERE INSTANCIA_PARCELA.FK_PARCELA = 1 AND NOMBRE = 'Finalizado' GROUP BY FK_CULTIVO) AS REST;

-- Obtiene el ID de los cultivos y la cantidad de veces que se repiten de aquellos cultivos que mas se plantaron en una parcela dada, la cual es elegida
-- en base a su ID
-- Un detalle muy imporante es que se hace esta consulta sobre los registros de parcela (instancias de parcela) que estan en el estado "Finalizado", el cual
-- tiene ID = 1

-- Primera versión de la consulta
SELECT FK_CULTIVO, COUNT(FK_CULTIVO) AS COUNT_CULTIVO
FROM INSTANCIA_PARCELA
WHERE INSTANCIA_PARCELA.FK_PARCELA = 1 AND INSTANCIA_PARCELA.FK_ESTADO = 1
GROUP BY FK_CULTIVO
HAVING COUNT(FK_CULTIVO) = (SELECT MAX(COUNT_CULTIVO) AS MAX_CULTIVO
FROM (SELECT COUNT(FK_CULTIVO) AS COUNT_CULTIVO FROM INSTANCIA_PARCELA WHERE INSTANCIA_PARCELA.FK_PARCELA = 1 AND INSTANCIA_PARCELA.FK_ESTADO = 1
GROUP BY FK_CULTIVO) AS REST);

-- Segunda versión de la consulta
SELECT FK_CULTIVO, COUNT(FK_CULTIVO) AS COUNT_CULTIVO
FROM INSTANCIA_PARCELA INNER JOIN INSTANCIA_PARCELA_ESTADO ON INSTANCIA_PARCELA.FK_ESTADO = INSTANCIA_PARCELA_ESTADO.INSTANCIA_PARCELA_ESTADO_ID
WHERE INSTANCIA_PARCELA.FK_PARCELA = 1 AND NOMBRE = 'Finalizado'
GROUP BY FK_CULTIVO
HAVING COUNT(FK_CULTIVO) = (SELECT MAX(COUNT_CULTIVO) AS MAX_CULTIVO
FROM (SELECT COUNT(FK_CULTIVO) AS COUNT_CULTIVO FROM INSTANCIA_PARCELA
INNER JOIN INSTANCIA_PARCELA_ESTADO ON INSTANCIA_PARCELA.FK_ESTADO = INSTANCIA_PARCELA_ESTADO.INSTANCIA_PARCELA_ESTADO_ID
WHERE INSTANCIA_PARCELA.FK_PARCELA = 1 AND NOMBRE = 'Finalizado'
GROUP BY FK_CULTIVO) AS REST);

-- Obtiene el nombre de aquellos cultivos que mas fueron plantados en una parcela dada, la cual es seleccionada en base a su ID (identificador)
-- La subconsulta de esta consulta contiene las tres consultas anteriores, lo que significa que la consulta, para obtener el cultivo que más se ha plantado
-- en una parcela dada, ha sido hecha usando las tres consultas anteriores
-- Un detalle muy imporante es que se hace esta consulta sobre los registros de parcela (instancias de parcela) que estan en el estado "Finalizado", el cual
-- tiene ID = 1

-- Primera versión de la consulta
SELECT NOMBRE
FROM CULTIVO INNER JOIN (SELECT FK_CULTIVO, COUNT(FK_CULTIVO) AS COUNT_CULTIVO FROM INSTANCIA_PARCELA WHERE INSTANCIA_PARCELA.FK_PARCELA = 1
AND INSTANCIA_PARCELA.FK_ESTADO = 1 GROUP BY FK_CULTIVO HAVING COUNT(FK_CULTIVO) = (SELECT MAX(COUNT_CULTIVO) AS MAX_CULTIVO 
FROM (SELECT COUNT(FK_CULTIVO) AS COUNT_CULTIVO FROM INSTANCIA_PARCELA WHERE INSTANCIA_PARCELA.FK_PARCELA = 1 AND INSTANCIA_PARCELA.FK_ESTADO = 1
GROUP BY FK_CULTIVO) AS REST)) AS REST ON CULTIVO_ID = REST.FK_CULTIVO;

-- Segunda versión de la consulta
SELECT NOMBRE
FROM CULTIVO INNER JOIN (SELECT FK_CULTIVO, COUNT(FK_CULTIVO) AS COUNT_CULTIVO FROM INSTANCIA_PARCELA
INNER JOIN INSTANCIA_PARCELA_ESTADO ON INSTANCIA_PARCELA.FK_ESTADO = INSTANCIA_PARCELA_ESTADO.INSTANCIA_PARCELA_ESTADO_ID
WHERE INSTANCIA_PARCELA.FK_PARCELA = 3 AND NOMBRE = 'Finalizado' GROUP BY FK_CULTIVO HAVING COUNT(FK_CULTIVO) = (SELECT MAX(COUNT_CULTIVO) AS MAX_CULTIVO 
FROM (SELECT COUNT(FK_CULTIVO) AS COUNT_CULTIVO FROM INSTANCIA_PARCELA
INNER JOIN INSTANCIA_PARCELA_ESTADO ON INSTANCIA_PARCELA.FK_ESTADO = INSTANCIA_PARCELA_ESTADO.INSTANCIA_PARCELA_ESTADO_ID
WHERE INSTANCIA_PARCELA.FK_PARCELA = 3 AND NOMBRE = 'Finalizado' GROUP BY FK_CULTIVO) AS REST)) AS REST ON CULTIVO_ID = REST.FK_CULTIVO;