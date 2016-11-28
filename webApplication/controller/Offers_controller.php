<?php
	class Offers_controller{
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
		function offers(){
			$this->renderView('offers');
		}
		function readPrimaryKeyOffer(){
			$offer = new stdClass();
			$offer->id = new stdClass();
			$offer->id->local = $_POST['place'];
			$offer->id->centroComercial = $_POST['mall'];
			$offer->id->direccionCentroComercial = $_POST['addressMall'];
			$offer->id->descripcion = $_POST['description'];
			$offer->id->locatario = $_POST['tenant'];
			return $offer;
		}
		function readOffer(){
			$offer = new stdClass();
			$offer->id = new stdClass();
			$offer->id->local = $_POST['place'];
			
			$statusUpload = Uploads::tryUploadImage('picture',$offer->id->local);
			if(substr($statusUpload,0,5) === 'ERROR'){
				return $statusUpload;
			}
			
			$offer->id->centroComercial = $_POST['mall'];
			$offer->id->direccionCentroComercial = $_POST['addressMall'];
			$offer->id->descripcion = $_POST['description'];
			$offer->id->locatario = $_POST['tenant'];
			$offer->inicio = $_POST['beginning'];
			$offer->expiracion = $_POST['expiration'];
			$offer->imagen = Image::imageToBase64(PATH_PICTURES . $statusUpload);
			return $offer;
		}
		function insertOffer(){
			Session::init();
			$offer = $this->readOffer();
			if(strcmp(gettype($offer), 'string') !== 0){
				$this->model->insertOffer($offer);
				Session::setValue('ALERT',new Alert('alert-warning','Oferta agregada a los registros'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$offer));
			}
		}
		function searchOffer(){
			Session::init();
			$offer = $this->readPrimaryKeyOffer();	
			$offer = $this->model->searchOffer($offer);
			if(isset($offer)){
				Session::setValue('OFFER',$offer);
			}else{
				Session::setValue('ALERT',new Alert('alert-warning','Oferta no encontrada'));
			}
		}
		function updateOffer(){
			Session::init();
			$offer = $this->readOffer();	
			if(strcmp(gettype($offer), 'string') !== 0){
				$this->model->updateOffer($offer);
				Session::setValue('ALERT',new Alert('alert-warning','Oferta modificada'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$offer));
			}
		}
		function deleteOffer(){
			Session::init();
			$offer = $this->readPrimaryKeyOffer();	
			//next 3 lines are nesesary for hibernet not give us a ERROR because the fields are empty, this is a fuck idiot!
			$offer->inicio = '1111-11-11';
			$offer->expiracion = '1111-11-11';
			$offer->descripcion = '';
			$offer->imagen = array();
			$this->model->deleteOffer($offer);
			Session::setValue('ALERT',new Alert('alert-warning','Oferta eliminado'));
		}
		function management(){
			if(isset($_POST['insert'])){
				$this->insertOffer();	
			}else{
				if(isset($_POST['search'])){
					$this->searchOffer();
				}else{
					if(isset($_POST['update'])){
						$this->updateOffer();
					}else{
						if(isset($_POST['delete'])){
							$this->deleteOffer();
						}
					}
				}
			}
			$this->renderView('offers');
		}
	}
?>
