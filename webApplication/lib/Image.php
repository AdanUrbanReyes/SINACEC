<?php
	class Image{
		static function base64ToImage($base64, $nameImage, $routeImage){
			$string = implode(array_map('chr', $base64));
			$string = explode(',',$string);
			sscanf($string[0],'data:image/%s;base64',$type);
			$type = explode(';',$type)[0];
			$nameImage = $nameImage . '.' . $type;	
			$file = fopen($routeImage . $nameImage, 'wb'); 
			fwrite($file, base64_decode($string[1])); 
			fclose($file);
			return $nameImage;
		}
		static function imageToBase64($pathImage){
			$type = pathinfo($pathImage, PATHINFO_EXTENSION);
			$data = file_get_contents($pathImage);
			$base64 = unpack('C*','data:image/' . $type . ';base64,' . base64_encode($data));
			return array_values($base64);
		}
	}
?>
