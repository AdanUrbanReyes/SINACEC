<?php
	class Uploads{
		static function tryUploadImage($nameInputFileHTML,$nameFile){
			$extencion=pathinfo($_FILES[$nameInputFileHTML]['name'],PATHINFO_EXTENSION);
			//$nameFile=explode('@',$_POST['cuenta'])[0].'.'.$extencion;
			$nameFile .= '.'.$extencion;
			$path=PATH_PICTURES.$nameFile;
			// Check if image file is a actual image or fake image
			$check = getimagesize($_FILES[$nameInputFileHTML]['tmp_name']);
			if($check === false) {
				return 'ERROR el archivo seleccionado no es una foto';
			}
			// Check if file already exists
	//		if (file_exists($path)) {
	//			return 'ERROR ya existe un archivo con el nombre ' . $nameFile;
	//		}
			// Check file size
			if ($_FILES[$nameInputFileHTML]['size'] > 500000) {
				return  'ERROR el archivo es demaciado grande (solo imagenes de no mas 500000 bytes)'; 
			}
			// Allow certain file formats
			if($extencion != "jpg" && $extencion != "png" && $extencion != "jpeg" && $extencion != "gif" ) {
				return 'ERROR solo aceptamos imagenes de formato JPG, JPEG, PNG y GIF';
			}
			if (!move_uploaded_file($_FILES[$nameInputFileHTML]['tmp_name'], $path)) {
				return 'ERROR al cargar la foto';
			}
			return $nameFile;
		}
	}
?>
