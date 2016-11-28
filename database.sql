CREATE SCHEMA sinacec;
USE sinacec;
CREATE TABLE codigo_postal(
	id INT NOT NULL AUTO_INCREMENT,
	codigo INT NOT NULL,
	estado VARCHAR(100) NOT NULL,
	municipio VARCHAR(100) NOT NULL,
	asentamiento VARCHAR(100) NOT NULL,
	PRIMARY KEY (id)
);
CREATE TABLE direccion(
	id INT AUTO_INCREMENT NOT NULL,
	codigo_postal INT NOT NULL,
	calle VARCHAR(100) NOT NULL,
	numero_exterior INT NOT NULL,
	numero_interior INT NULL,
	PRIMARY KEY(id)
);
CREATE TABLE usuario(
	cuenta VARCHAR(45) NOT NULL,
	clave VARCHAR(45) NOT NULL,
	nombres VARCHAR(30) NOT NULL,
	primer_apellido VARCHAR(30) NOT NULL,
	segundo_apellido VARCHAR(30) NULL,
	imagen MEDIUMBLOB NOT NULL,
	tipo CHAR NOT NULL,
	PRIMARY KEY (cuenta)
);
CREATE TABLE centro_comercial(
	nombre VARCHAR(70) NOT NULL,
	direccion INT NOT NULL,
	administrador VARCHAR(45) NOT NULL,
	horario_apertura TIME NOT NULL,
	horario_cierre TIME NOT NULL,
	imagen MEDIUMBLOB NOT NULL,
	telefono VARCHAR(13) NULL,
	sitio_web VARCHAR(70) NULL,
	PRIMARY KEY(nombre, direccion)
);
CREATE TABLE area(
	nombre VARCHAR(50) NOT NULL,
	centro_comercial VARCHAR(70) NOT NULL,
	direccion_centro_comercial INT NOT NULL,
	latitud DOUBLE NOT NULL,
	longitud DOUBLE NOT NULL,
	imagen MEDIUMBLOB NOT NULL,
	PRIMARY KEY (nombre, centro_comercial, direccion_centro_comercial)
);
CREATE TABLE local(
	local VARCHAR(11) NOT NULL,
	centro_comercial VARCHAR(70) NOT NULL,
	direccion_centro_comercial INT NOT NULL,
	area VARCHAR(50) NOT NULL,
	estatus CHAR NOT NULL,
	PRIMARY KEY(local, centro_comercial, direccion_centro_comercial)
);
CREATE TABLE local_rentado(
	local VARCHAR(11) NOT NULL,
	centro_comercial VARCHAR(70) NOT NULL,
	direccion_centro_comercial INT NOT NULL,
	locatario VARCHAR(45) NOT NULL,
	nombre VARCHAR(50) NOT NULL,
	giro VARCHAR(50) NOT NULL,
	horario_apertura TIME NOT NULL,
	horario_cierre TIME NOT NULL,
	telefono VARCHAR(13) NULL,
	sitio_web VARCHAR(70) NULL,
	imagen MEDIUMBLOB NOT NULL,
	PRIMARY KEY(local, centro_comercial, direccion_centro_comercial)
);
CREATE TABLE puerta(
	latitud DOUBLE NOT NULL,
	longitud DOUBLE NOT NULL,
	local VARCHAR(11) NOT NULL,
	centro_comercial VARCHAR(70) NOT NULL,
	direccion_centro_comercial INT NOT NULL,
	tipo CHAR NOT NULL,
	PRIMARY KEY(latitud, longitud, local, centro_comercial, direccion_centro_comercial)
);
CREATE TABLE servicio(
	nombre VARCHAR(30) NOT NULL,
	local VARCHAR(11) NOT NULL,
	centro_comercial VARCHAR(70) NOT NULL,
	direccion_centro_comercial INT NOT NULL,
	descripcion VARCHAR(150) NOT NULL,
	imagen MEDIUMBLOB NOT NULL,
	PRIMARY KEY (nombre, local, centro_comercial, direccion_centro_comercial)
);
CREATE TABLE producto(
	nombre VARCHAR(30) NOT NULL,
	local VARCHAR(11) NOT NULL,
	centro_comercial VARCHAR(70) NOT NULL,
	direccion_centro_comercial INT NOT NULL,
	descripcion VARCHAR(150) NOT NULL,
	imagen MEDIUMBLOB NOT NULL,
	PRIMARY KEY (nombre, local, centro_comercial, direccion_centro_comercial)
);
CREATE TABLE oferta(
	descripcion VARCHAR(150) NOT NULL,
	local VARCHAR(11) NOT NULL,
	centro_comercial VARCHAR(70) NOT NULL,
	direccion_centro_comercial INT NOT NULL,
	locatario VARCHAR(45) NOT NULL,
	inicio DATE NOT NULL,
	expiracion DATE NOT NULL,
	imagen MEDIUMBLOB NOT NULL,
	PRIMARY KEY (descripcion, local, centro_comercial, direccion_centro_comercial, locatario)
);
CREATE TABLE registro_locatario(
	cuenta VARCHAR(45) NOT NULL,
	local VARCHAR(11) NOT NULL,
	centro_comercial VARCHAR(70) NOT NULL,
	direccion_centro_comercial INT NOT NULL,
	clave VARCHAR(45) NOT NULL,
	nombres VARCHAR(30) NOT NULL,
	primer_apellido VARCHAR(30) NOT NULL,
	segundo_apellido VARCHAR(30) NOT NULL,
	imagen MEDIUMBLOB NOT NULL,
	nombre_local VARCHAR(50) NOT NULL,
	giro VARCHAR(50) NOT NULL,
	horario_apertura TIME NOT NULL,
	horario_cierre TIME NOT NULL,
	telefono VARCHAR(13) NULL,
	sitio_web VARCHAR(70) NULL,
	imagen_local MEDIUMBLOB NOT NULL,
	PRIMARY KEY(cuenta)
);
INSERT INTO local VALUES 
('1371-PB','Forum Buenavista',1,'Comida','L'),
('1410-SP','Forum Buenavista',1,'Calzado','L');

INSERT INTO registro_locatario VALUES
('aeb@g.com','1410-PB','Forum Buenavista',1,'2','Alexa','Escobedo','Bermudez','SIN IMAGEN','Fashion Store','Ropa y Calzado','08:00:00','19:00:00','58921129','www.alexa.com','SIN IMAGEN'),
('aml@g.com','1371-PB','Forum Buenavista',1,'2','Abigail','Martinez','Lopez','SIN IMAGEN','Fast Food','Comida','07:00:00','22:00:00','24600063','www.abigail.com','SIN IMAGEN')
;

INSERT INTO `codigo_postal` VALUES (1,6350,'Ciudad de México','Cuauhtémoc','Buenavista'),(2,7700,'Ciudad de México','Gustavo A. Madero','Nueva Industrial Vallejo');
INSERT INTO `direccion` VALUES (1,1,'Eje 1 equina con Av. Insurgentes',11,NULL),(2,2,'Avenida Miguel oton',17,NULL);
INSERT INTO usuario VALUES('aur@g.com','1','Adan','Urban','Reyes','SIN IMAGEN','C');
INSERT INTO centro_comercial VALUES('Forum Buenavista',1,'aur@g.com','07:00:00','20:00:00','SIN IMAGEN','5571648976','www.forumbuenavista.com');

-- ALTER TABLE direccion ADD FOREIGN KEY (codigo_postal) REFERENCES codigo_postal(id) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE centro_comercial ADD FOREIGN KEY (direccion) REFERENCES direccion(id) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE centro_comercial ADD FOREIGN KEY (administrador) REFERENCES usuario(cuenta) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE area ADD FOREIGN KEY (centro_comercial, direccion_centro_comercial) REFERENCES centro_comercial(nombre, direccion) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE local ADD FOREIGN KEY (centro_comercial, direccion_centro_comercial, area) REFERENCES area(centro_comercial, direccion_centro_comercial, nombre) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE local_rentado ADD FOREIGN KEY (local, centro_comercial, direccion_centro_comercial) REFERENCES local(local, centro_comercial, direccion_centro_comercial) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE local_rentado ADD FOREIGN KEY (locatario) REFERENCES usuario(cuenta) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE puerta ADD FOREIGN KEY (local, centro_comercial, direccion_centro_comercial) REFERENCES local_rentado(local, centro_comercial, direccion_centro_comercial) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE servicio ADD FOREIGN KEY (local, centro_comercial, direccion_centro_comercial) REFERENCES local_rentado(local, centro_comercial, direccion_centro_comercial) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE producto ADD FOREIGN KEY (local, centro_comercial, direccion_centro_comercial) REFERENCES local_rentado(local, centro_comercial, direccion_centro_comercial) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE oferta ADD FOREIGN KEY (local, centro_comercial, direccion_centro_comercial) REFERENCES local_rentado(local, centro_comercial, direccion_centro_comercial) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE oferta ADD FOREIGN KEY(locatario) REFERENCES usuario(cuenta) ON UPDATE CASCADE ON DELETE CASCADE;
-- ALTER TABLE registro_locatario ADD FOREIGN KEY(local, centro_comercial, direccion_centro_comercial) REFERENCES local(local, centro_comercial, direccion_centro_comercial) ON UPDATE CASCADE ON DELETE CASCADE;
