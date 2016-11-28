<?php
	require PATH_MODEL.'Alert.php';
	class Login_model{
		function validateSignIn($account, $password){
			return XML::xmlToObject(WebService::validarInicioSesion($account, $password));
		}
		function getMalls($account){
			return XML::xmlToObject(WebService::leer('{"cuenta" : "'.$account.'"}', 'CentroComercial'));
		}
		function getPlaces($account){
			return XML::xmlToObject(WebService::leer('{"locatario" : "'.$account.'"}', 'LocalRentado'));
		}
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
	}
?>
