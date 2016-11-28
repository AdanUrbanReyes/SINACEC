<?php
	class Mall_controller{
		function __construct(){
			$this->loadModel();
		}
		function renderView($view){
			$pathView = PATH_VIEW . $view . '.php';
			if(file_exists($pathView)){
				require $pathView;
			}
		}
		function loadModel(){
			$model = str_replace('_controller', '_model',get_class($this));
			$pathModel = PATH_MODEL . $model . '.php';
			if(file_exists($pathModel)){
				require $pathModel;
				$this->model = new $model();
			}
		}
		function selection(){
			$this->renderView('selectionMall');	
		}
		function setInformationMall($mall){
			$address = $this->model->getAddress($mall->id->direccion);
			// here we can validate if there are address with a if(isset($address))
			$postalCode = $this->model->getPostalCode($address->codigoPostal);
			// here we can validate if there are postalCode with a if(isset($postalCode))
			$areas = $this->model->getAreas($mall->id->nombre, $mall->id->direccion);
			Session::setValue('ADDRESS_MALL', $address);
			Session::setValue('POSTAL_CODE_MALL', $postalCode);
			if( isset($areas) ){
				Session::setValue('AREAS',$areas);
			}else{
				Session::removeValue('AREAS');
			}
		}
		function select(){
			Session::init();
			$malls = Session::getValue('MALLS');
			$indexMall = $_POST['indexMall'];
			Session::setValue('INDEX_MALL',$indexMall);
			$this->setInformationMall($malls[$indexMall]);
			$this->renderView('main');
		}
		function myMall(){
			$this->renderView('myMall');
		}
		function readMall(){
			$mall = new stdClass();
			$mall->id = new stdClass();
			$mall->id->nombre = $_POST['nombre'];
			$mall->id->direccion = $_POST['direccion'];
			$mall->administrador = $_POST['administrador'];
			$mall->horarioApertura = $_POST['horarioApertura'];
			$mall->horarioCierre = $_POST['horarioCierre'];

			$statusUpload = Uploads::tryUploadImage('foto', str_replace(' ','',$mall->id->nombre) . $mall->id->direccion);
			if(substr($statusUpload,0,5) === 'ERROR'){
				return $statusUpload;
			}
			$mall->imagen = Image::imageToBase64(PATH_PICTURES . $statusUpload);
			
			$mall->telefono = $_POST['telefono'];
			$mall->sitioWeb= $_POST['sitioWeb'];
			return $mall;
		}
		function modify(){
			Session::init();
			if(isset($_POST['submit'])){
				$mall = $this->readMall();
				if(strcmp(gettype($mall), 'string') !== 0){
					$response = $this->model->update($mall);
					// validate reponse is missing 
					$malls = Session::getValue('MALLS');
					$index = Session::getValue('INDEX_MALL');
					$malls[$index] = $mall;
					Session::setValue('MALLS', $malls);
					Session::setValue('ALERT',new Alert('alert-warning','Centro Comercial modificado'));
				}else{
					Session::setValue('ALERT',new Alert('alert-warning', $mall));
				}
			}else{
				Session::setValue('ALERT',new Alert('alert-warning','No se a presionado el boton de modificar'));
			}
			$this->renderView('main');
		}
	}
?>
