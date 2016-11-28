<?php
	require PATH_MODEL . 'Alert.php';
	class Offers_model{
		function insertOffer($offer){
			return XML::xmlToObject(WebService::crear(json_encode($offer), 'Oferta'));
		}
		function searchOffer($offer){
			$offers = XML::xmlToObject(WebService::leer(json_encode($offer), 'Oferta'));
			return ( isset($offers) ) ? $offers[0] : null;  
		}
		function updateOffer($offer){
			return XML::xmlToObject(WebService::actualizar(json_encode($offer), 'Oferta'));
		}
		function deleteOffer($offer){
			return XML::xmlToObject(WebService::eliminar(json_encode($offer), 'Oferta'));
		}
	}
?>
