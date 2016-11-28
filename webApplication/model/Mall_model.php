<?php
	require PATH_MODEL . 'Alert.php';
	class Mall_model{
		function getAddress($id){
			$addresses = XML::xmlToObject(WebService::leer('{"id" : '.$id.'}', 'Direccion'));
			return ( isset($addresses) ) ? $addresses[0] : null ;
		}
		function getPostalCode($id){
			$postalCodes = XML::xmlToObject(WebService::leer('{"id" : '.$id.'}', 'CodigoPostal'));
			return ( isset($postalCodes) ) ? $postalCodes[0] : null;
		}
		function getAreas($nameMall, $addressMall){
			return XML::xmlToObject(WebService::leer('{"centro_comercial" : "' . $nameMall . '", "direccion_centro_comercial" : ' .  $addressMall . '}','Area'));
		}
		function update($mall){
			return XML::xmlToObject(WebService::actualizar(json_encode($mall),'CentroComercial'));
		}	
	}
?>
