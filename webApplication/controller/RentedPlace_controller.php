<?php
	class RentedPlace_controller{
		function __construct(){
			$this->loadModel();
		}
		function renderView($view){
			$pathView=PATH_VIEW.$view.'.php';
			if(file_exists($pathView)){
				require $pathView;
			}
		}
		function loadModel(){
			$model=str_replace('_controller','_model',get_class($this));
			$pathModel=PATH_MODEL.$model.'.php';
			if(file_exists($pathModel)){
				require $pathModel;
				$this->model=new $model();
			}
		}
		function selection(){
			$this->renderView('selectionRentedPlace');	
		}
		function select(){
			Session::init();
			$indexRentedPlace = $_POST['indexRentedPlace'];
			Session::setValue('INDEX_RENTED_PLACE',$indexRentedPlace);
			$this->renderView('main');
		}
		function readRentedPlace(){
			$rentedPlace = new stdClass();	
			$rentedPlace->id = new stdClass();	
			$rentedPlace->id->local = $_POST['place'];
			
			$statusUpload = Uploads::tryUploadImage('picture',$rentedPlace->id->local);
			if(substr($statusUpload,0,5) === 'ERROR'){
				return $statusUpload;
			}

			$rentedPlace->id->centroComercial = $_POST['mall'];
			$rentedPlace->id->direccionCentroComercial = $_POST['addressMall'];
			$rentedPlace->locatario = $_POST['tenant'];
			$rentedPlace->nombre = $_POST['name'];
			$rentedPlace->horarioApertura = $_POST['openingHours'];
			$rentedPlace->horarioCierre = $_POST['closingHours'];
			$rentedPlace->telefono = $_POST['phone'];
			$rentedPlace->sitioWeb = $_POST['webSide'];
			$rentedPlace->imagen = Image::imageToBase64(PATH_PICTURES . $statusUpload);
			return $rentedPlace;
		}
		function myRentedPlace(){
			$this->renderView('myRentedPlace');
		}
		function updateRentedPlace(){
			Session::init();
			$rentedPlace = $this->readRentedPlace();	
			if(strcmp(gettype($rentedPlace), 'string') !== 0){
				$response = $this->model->updateRentedPlace($rentedPlace);
				//validate response is missing
				$rentedPlaces = Session::getValue('RENTED_PLACES');
				$index = Session::getValue('INDEX_RENTED_PLACE');
				$rentedPlaces[$index] = $rentedPlace;
				Session::setValue('RENTED_PLACES',$rentedPlaces);
				Session::setValue('ALERT',new Alert('alert-warning','Local modificado'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$rentedPlace));
			}
			$this->renderView('main');
		}
	}
?>
