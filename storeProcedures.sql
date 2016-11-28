DELIMITER #
CREATE PROCEDURE sign_up_validator(IN account VARCHAR(45), IN estado CHAR)
BEGIN
	DECLARE place VARCHAR(11);
	DECLARE mall VARCHAR(70);
	DECLARE addressMall INT;
	IF(estado = 'A') THEN
	
		SELECT local, centro_comercial, direccion_centro_comercial INTO place,mall,addressMall
		FROM registro_locatario
		WHERE cuenta = account;

		UPDATE local 
		SET estatus = 'O' 
		WHERE local = place 
			AND centro_comercial = mall 
			AND direccion_centro_comercial = addressMall;
	
		INSERT INTO local_rentado 
		SELECT local, centro_comercial, direccion_centro_comercial, cuenta, nombre_local, giro, horario_apertura, horario_cierre, telefono, sitio_web, imagen_local
		FROM registro_locatario
		WHERE cuenta = account;
	
	
		INSERT INTO usuario
		SELECT cuenta, clave, nombres, primer_apellido, segundo_apellido, imagen, 'L'
		FROM registro_locatario
		WHERE cuenta = account;
	
		DELETE FROM registro_locatario
		WHERE local = place 
			AND centro_comercial = mall 
			AND direccion_centro_comercial = addressMall;
	ELSE 	
		DELETE FROM registro_locatario
		WHERE cuenta = account;
	END IF;

END #
DELIMITER ;

DELIMITER #
CREATE PROCEDURE search_rented_shops(IN key_word VARCHAR(50),IN tenant VARCHAR(45), IN mall VARCHAR(70), IN addressMall INT)
BEGIN
	SELECT rs.*
	FROM local_rentado rs
	WHERE rs.centro_comercial = mall AND
		rs.direccion_centro_comercial = addressMall AND
		rs.nombre LIKE CONCAT('%',key_word,'%') AND
		IF(tenant = 'NULL', rs.locatario IS NOT NULL, rs.locatario = tenant)
	;
END #
DELIMITER ;

DELIMITER #
CREATE PROCEDURE search_products(IN key_word VARCHAR(30),IN shop VARCHAR(11), IN mall VARCHAR(70), IN addressMall INT)
BEGIN
	SELECT p.*
	FROM producto p
	WHERE p.centro_comercial = mall AND
		p.direccion_centro_comercial = addressMall AND
		p.nombre LIKE CONCAT('%',key_word,'%') AND
		IF(shop = 'NULL', p.local IS NOT NULL, p.local = shop)
	;
END #
DELIMITER ;

DELIMITER #
CREATE PROCEDURE search_services(IN key_word VARCHAR(30),IN shop VARCHAR(11), IN mall VARCHAR(70), IN addressMall INT)
BEGIN
	SELECT s.*
	FROM servicio s
	WHERE s.centro_comercial = mall AND
		s.direccion_centro_comercial = addressMall AND
		s.nombre LIKE CONCAT('%',key_word,'%') AND
		IF(shop = 'NULL', s.local IS NOT NULL, s.local = shop)
	;
END #
DELIMITER ;

DELIMITER #
CREATE PROCEDURE malls_tenant(IN account VARCHAR(45))
BEGIN
	SELECT m.*
	FROM local_rentado rs,
		centro_comercial m
	WHERE rs.locatario = account AND
		m.nombre = rs.centro_comercial AND
		m.direccion = rs.direccion_centro_comercial;
END #
DELIMITER ;

DELIMITER #
CREATE PROCEDURE get_rented_places_on_area(IN area VARCHAR(50), IN centro_comercial VARCHAR(70), IN direccion_centro_comercial INT)
BEGIN
	SELECT lr.*
	FROM area a,
		local l,
		local_rentado lr
	WHERE a.nombre = area AND
		a.centro_comercial = centro_comercial AND
		a.direccion_centro_comercial = direccion_centro_comercial AND
		l.area = a.nombre AND
		l.centro_comercial = a.centro_comercial AND
		l.direccion_centro_comercial = direccion_centro_comercial AND
		l.estatus = 'O' AND
		lr.local = l.local AND
		lr.centro_comercial = l.centro_comercial AND
		lr.direccion_centro_comercial = l.direccion_centro_comercial
	;
END #
DELIMITER ;

DELIMITER #
CREATE PROCEDURE get_places_on_area(IN area VARCHAR(50), IN centro_comercial VARCHAR(70), IN direccion_centro_comercial INT)
BEGIN
	SELECT l.*
	FROM area a,
		local l
	WHERE a.nombre = area AND
		a.centro_comercial = centro_comercial AND
		a.direccion_centro_comercial = direccion_centro_comercial AND
		l.area = a.nombre AND
		l.centro_comercial = a.centro_comercial AND
		l.direccion_centro_comercial = direccion_centro_comercial
	;
END #
DELIMITER ;
