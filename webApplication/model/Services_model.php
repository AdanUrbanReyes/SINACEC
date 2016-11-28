<?php
	require PATH_MODEL . 'Alert.php';
	class Services_model{
		function insertService($service){
			return XML::xmlToObject(WebService::crear(json_encode($service), 'Servicio'));
		}
		function searchService($service){
			$services = XML::xmlToObject(WebService::leer(json_encode($service), 'Servicio'));
			return ( isset($services) ) ? $services[0] : null;  
		}
		function updateService($service){
			return XML::xmlToObject(WebService::actualizar(json_encode($service), 'Servicio'));
		}
		function deleteService($service){
			return XML::xmlToObject(WebService::eliminar(json_encode($service), 'Servicio'));
		}
	}
?>
