<?php
	class Products_controller{
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
		function products(){
			$this->renderView('products');
		}
		function readPrimaryKeyProduct(){
			$product = new stdClass();
			$product->id = new stdClass();
			$product->id->nombre = $_POST['nameProduct'];
			$product->id->local = $_POST['place'];
			$product->id->centroComercial = $_POST['mall'];
			$product->id->direccionCentroComercial = $_POST['addressMall'];
			return $product;
		}
		function readProduct(){
			$product = new stdClass();
			$product->id = new stdClass();
			$product->id->nombre = $_POST['nameProduct'];
			
			$statusUpload = Uploads::tryUploadImage('picture',$product->id->nombre);
			if(substr($statusUpload,0,5) === 'ERROR'){
				return $statusUpload;
			}
			
			$product->id->local = $_POST['place'];
			$product->id->centroComercial = $_POST['mall'];
			$product->id->direccionCentroComercial = $_POST['addressMall'];
			$product->descripcion = $_POST['description'];
			$product->imagen = Image::imageToBase64(PATH_PICTURES . $statusUpload);
			return $product;
		}
		function insertProduct(){
			Session::init();
			$product = $this->readProduct();
			if(strcmp(gettype($product), 'string') !== 0){
				$this->model->insertProduct($product);
				Session::setValue('ALERT',new Alert('alert-warning','Producto agregado a los registros'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$product));
			}
		}
		function searchProduct(){
			Session::init();
			$product = $this->readPrimaryKeyProduct();	
			$product = $this->model->searchProduct($product);
			if(isset($product)){
				Session::setValue('PRODUCT',$product);
			}else{
				Session::setValue('ALERT',new Alert('alert-warning','Producto no encontrado'));
			}
		}
		function updateProduct(){
			Session::init();
			$product = $this->readProduct();	
			if(strcmp(gettype($product), 'string') !== 0){
				$this->model->updateProduct($product);
				Session::setValue('ALERT',new Alert('alert-warning','Producto modificado'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$product));
			}
		}
		function deleteProduct(){
			Session::init();
			$product = $this->readPrimaryKeyProduct();	
			//next 3 lines are nesesary for hibernet not give us a ERROR because the fields are empty, this is a fuck idiot!	
			$product->descripcion = '';
			$product->imagen = array();
			$this->model->deleteProduct($product);
			Session::setValue('ALERT',new Alert('alert-warning','Producto eliminado'));
		}
		function management(){
			if(isset($_POST['insert'])){
				$this->insertProduct();	
			}else{
				if(isset($_POST['search'])){
					$this->searchProduct();
				}else{
					if(isset($_POST['update'])){
						$this->updateProduct();
					}else{
						if(isset($_POST['delete'])){
							$this->deleteProduct();
						}
					}
				}
			}
			$this->renderView('products');
		}
	}
?>
