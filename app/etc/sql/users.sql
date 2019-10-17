-- DELETES
DELETE FROM USUARIO;
ALTER TABLE USUARIO ALTER COLUMN USUARIO_ID RESTART WITH 1;

-- INSERTS

-- Usuarios administradores
-- Nombre de usuario: admin Contraseña: admin
INSERT INTO USUARIO (USUARIO, PASSWORD, SUPER_USUARIO) VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', 1);

-- Usuarios clientes del sistema
-- Nombre de usuario: mretes Contraseña: 1234
INSERT INTO USUARIO (USUARIO, NOMBRE, APELLIDO, PASSWORD, DNI, DIRECCION, TELEFONO, EMAIL, ESTADO, SUPER_USUARIO) VALUES ('mreyes', 'Milagros', 'Reyes', '81dc9bdb52d04dc20036dbd8313ed055', '48052150',
  'Zarlach 1064', '280434', 'mreyes@gmail.com', 'ALTA', 0);
