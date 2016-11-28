<?php
	class Services_controller{
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
		function services(){
			$this->renderView('services');
		}
		function readPrimaryKeyService(){
			$service = new stdClass();
			$service->id = new stdClass();
			$service->id->nombre = $_POST['nameService'];
			$service->id->local = $_POST['place'];
			$service->id->centroComercial = $_POST['mall'];
			$service->id->direccionCentroComercial = $_POST['addressMall'];
			return $service;
		}
		function readService(){
			$service = new stdClass();
			$service->id = new stdClass();
			$service->id->nombre = $_POST['nameService'];
			
			$statusUpload = Uploads::tryUploadImage('picture',$service->id->nombre);
			if(substr($statusUpload,0,5) === 'ERROR'){
				return $statusUpload;
			}
			
			$service->id->local = $_POST['place'];
			$service->id->centroComercial = $_POST['mall'];
			$service->id->direccionCentroComercial = $_POST['addressMall'];
			$service->descripcion = $_POST['description'];
			$service->imagen = Image::imageToBase64(PATH_PICTURES . $statusUpload);
			return $service;
		}
		function insertService(){
			Session::init();
			$service = $this->readService();
			if(strcmp(gettype($service), 'string') !== 0){
				$this->model->insertService($service);
				Session::setValue('ALERT',new Alert('alert-warning','Servicio agregado a los registros'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$service));
			}
		}
		function searchService(){
			Session::init();
			$service = $this->readPrimaryKeyService();	
			$service = $this->model->searchService($service);
			if(isset($service)){
				Session::setValue('SERVICE',$service);
			}else{
				Session::setValue('ALERT',new Alert('alert-warning','Serviceo no encontrado'));
			}
		}
		function updateService(){
			Session::init();
			$service = $this->readService();	
			if(strcmp(gettype($service), 'string') !== 0){
				$this->model->updateService($service);
				Session::setValue('ALERT',new Alert('alert-warning','Servicio modificado'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$service));
			}
		}
		function deleteService(){
			Session::init();
			$service = $this->readPrimaryKeyService();	
			//next 3 lines are nesesary for hibernet not give us a ERROR because the fields are empty, this is a fuck idiot!	
			$service->descripcion = '';
			$service->imagen = array();
			$this->model->deleteService($service);
			Session::setValue('ALERT',new Alert('alert-warning','Servicio eliminado'));
		}
		function management(){
			if(isset($_POST['insert'])){
				$this->insertService();	
			}else{
				if(isset($_POST['search'])){
					$this->searchService();
				}else{
					if(isset($_POST['update'])){
						$this->updateService();
					}else{
						if(isset($_POST['delete'])){
							$this->deleteService();
						}
					}
				}
			}
			$this->renderView('services');
		}
	}
?>
