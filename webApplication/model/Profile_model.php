<?php
	require PATH_MODEL.'Alert.php';
	class Profile_model{
		function update($user){
			return XML::xmlToObject(WebService::actualizar(json_encode($user),'Usuario'));
		}	
	}
?>
