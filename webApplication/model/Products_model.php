<?php
	require PATH_MODEL . 'Alert.php';
	class Products_model{
		function insertProduct($product){
			return XML::xmlToObject(WebService::crear(json_encode($product), 'Producto'));
		}
		function searchProduct($product){
			$products = XML::xmlToObject(WebService::leer(json_encode($product), 'Producto'));
			return ( isset($products) ) ? $products[0] : null;  
		}
		function updateProduct($product){
			return XML::xmlToObject(WebService::actualizar(json_encode($product), 'Producto'));
		}
		function deleteProduct($product){
			return XML::xmlToObject(WebService::eliminar(json_encode($product), 'Producto'));
		}
	}
?>
