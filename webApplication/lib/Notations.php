<?php
	class Notations{
		function notacionGuionesA($notacionGuiones){
			if(isset($notacionGuiones)){
				return NULL;
			}
			$camelCase='';
			$bandera=false;
			for($i=0;$i<strlen($notacionGuiones);$i++){
				if($notacionGuiones[$i]=='_'){
					$bandera=true;
					continue;
				}
				$camelCase .= ($bandera && $camelCase != '') ? strtoupper($notacionGuiones[$i]) : $notacionGuiones[$i];
				$bandera=false;
			}
			return $camelCase;
		}	
	}
?>
