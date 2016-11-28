<?php
	//blog.osusnet.com/2008/08/06/consumiendo-webservices-soap-desde-php/
	class WebService{
		static function validarInicioSesion($account, $password){
			$parameters = array();
			$parameters['cuenta'] = $account;
			$parameters['clave'] = $password;
			$client = new SoapClient(URL_WEBSERVICE, $parameters);
			return $client->validarInicioSesion($parameters); // call method of webservice
		}
		static function crear($json, $className){
			$parameters = array();
			$parameters['json'] = $json;
			$parameters['nombreClase'] = $className;
			$client = new SoapClient(URL_WEBSERVICE, $parameters);
			return $client->crear($parameters);
		} 
		static function leer($json, $className){
			$parameters = array();
			$parameters['json'] = $json;
			$parameters['nombreClase'] = $className;
			$client = new SoapClient(URL_WEBSERVICE, $parameters);
			return $client->leer($parameters);
		} 
		static function leerSinImagen($json, $className){
			$parameters = array();
			$parameters['json'] = $json;
			$parameters['nombreClase'] = $className;
			$client = new SoapClient(URL_WEBSERVICE, $parameters);
			return $client->leerSinImagen($parameters);
		} 
		static function actualizar($json, $className){
			$parameters = array();
			$parameters['json'] = $json;
			$parameters['nombreClase'] = $className;
			$client = new SoapClient(URL_WEBSERVICE, $parameters);
			return $client->actualizar($parameters);
		}
		static function eliminar($json, $className){
			$parameters = array();
			$parameters['json'] = $json;
			$parameters['nombreClase'] = $className;
			$client = new SoapClient(URL_WEBSERVICE, $parameters);
			return $client->eliminar($parameters);
		}
		static function validarRegistroLocatario($account, $state){
			$parameters = array();
			$parameters['cuenta'] = $account;
			$parameters['estado'] = $state;
			$client = new SoapClient(URL_WEBSERVICE, $parameters);
			//return $client->llamarProcedimientoSelect($parameters);	
			return $client->validarRegistroLocatario($parameters);	
		}
		static function busqueda($json, $className){
			$parameters = array();
			$parameters['json'] = $json;
			$parameters['nombreClase'] = $className;
			$client = new SoapClient(URL_WEBSERVICE, $parameters);
			return $client->busqueda($parameters);
		} 
	}
?>
