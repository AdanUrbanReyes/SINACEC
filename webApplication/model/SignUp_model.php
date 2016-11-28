<?php
	require PATH_MODEL . 'Alert.php';
	class SignUp_model{
		function getMalls(){
			return XML::xmlToArray(WebService::leerSinImagen('','CentroComercial'))['return'];
		}
		function getFreePlaces($nameMall, $addressMall){
			return XML::xmlToArray(WebService::leerSinImagen('{"id" : {"centroComercial" : "' .$nameMall . '", "direccionCentroComercial" : ' . $addressMall . ' }, "estatus" : "L"}','Local'))['return'];
		}
		function insertSignUp($signUp){
			return XML::xmlToObject(WebService::crear(json_encode($signUp), 'RegistroLocatario'));
		}
		function getSignUp($mall, $addressMall){
			return XML::xmlToObject(WebService::leerSinImagen('{"centroComercial" : "' . $mall . '", "direccionCentroComercial" : ' . $addressMall. '}','RegistroLocatario'));
		}
		function validateSignUp($account, $state){
			return XML::xmlToObject(WebService::validarRegistroLocatario($account, $state));
		}
	}
?>
