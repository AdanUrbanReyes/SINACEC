<?php
	require PATH_MODEL . 'Alert.php';
	class Places_model{
		function insertPlace($place){
			return XML::xmlToObject(WebService::crear(json_encode($place), 'Local'));
		}
		function searchPlace($place){
			$places = XML::xmlToObject(WebService::leer(json_encode($place), 'Local'));
			return ( isset($places) ) ? $places[0] : null;  
		}
		function updatePlace($place){
			return XML::xmlToObject(WebService::actualizar(json_encode($place), 'Local'));
		}
		function deletePlace($place){
			return XML::xmlToObject(WebService::eliminar(json_encode($place), 'Local'));
		}
	}
?>
