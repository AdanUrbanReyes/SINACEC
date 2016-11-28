<?php
	class Places_controller{
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
		function places(){
			$this->renderView('places');
		}
		function readPrimaryKeyPlace(){
			$place = new stdClass();
			$place->id = new stdClass();
			$place->id->local = $_POST['place'];
			$place->id->centroComercial = $_POST['nameMall'];
			$place->id->direccionCentroComercial = intval($_POST['addressMall']);
			return $place;
		}
		function readPlace(){
			$place = new stdClass();
			$place->id = new stdClass();
			$place->id->local = $_POST['place'];
			
			$statusUpload = Uploads::tryUploadImage('picture',$place->id->local);
			if(substr($statusUpload,0,5) === 'ERROR'){
				return $statusUpload;
			}

			$place->id->centroComercial = $_POST['nameMall'];
			$place->id->direccionCentroComercial = intval($_POST['addressMall']);
			$place->area = $_POST['area'];
			$place->estatus = $_POST['status'];
			$place->imagen = Image::imageToBase64(PATH_PICTURES . $statusUpload);
			return $place;
		}
		function insertPlace(){
			Session::init();
			$place = $this->readPlace();	
			if(strcmp(gettype($place), 'string') !== 0){
				$this->model->insertPlace($place);
				Session::setValue('ALERT',new Alert('alert-warning','Local insertado'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$place));
			}
		}
		function searchPlace(){
			Session::init();
			$place = $this->readPrimaryKeyPlace();	
			$place = $this->model->searchPlace($place);
			if(isset($place)){
				Session::setValue('PLACE',$place);
			}else{
				Session::setValue('ALERT',new Alert('alert-warning','Local no encontrado'));
			}
		}
		function updatePlace(){
			Session::init();
			$place = $this->readPlace();	
			if(strcmp(gettype($place), 'string') !== 0){
				$this->model->updatePlace($place);
				Session::setValue('ALERT',new Alert('alert-warning','Local modificado'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$place));
			}
		}
		function deletePlace(){
			Session::init();
			$place = $this->readPrimaryKeyPlace();	
			//next 3 lines are nesesary for hibernet not give us a ERROR because the fields are empty, this is a fuck idiot!	
			$place->area = '';
			$place->estatus = 'E';
			$place->imagen = array();
			$this->model->deletePlace($place);
			Session::setValue('ALERT',new Alert('alert-warning','Local eliminado'));
		}
		function management(){
			if(isset($_POST['insert'])){
				$this->insertPlace();	
			}else{
				if(isset($_POST['search'])){
					$this->searchPlace();
				}else{
					if(isset($_POST['update'])){
						$this->updatePlace();
					}else{
						if(isset($_POST['delete'])){
							$this->deletePlace();
						}
					}
				}
			}
			$this->renderView('places');
		}
	}
?>
