<?php
	require PATH_MODEL . 'Alert.php';
	class RentedPlace_model{
		function updateRentedPlace($rentedPlace){
			return XML::xmlToObject(WebService::actualizar(json_encode($rentedPlace), 'LocalRentado'));
		}
	}
?>
