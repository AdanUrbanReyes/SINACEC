<?php
	class SignUp_controller{
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
		function fillSignUp(){
			$this->renderView('signUp');	
		}
		function validation(){
			Session::init();
			$malls = Session::getValue('MALLS');
			$index = Session::getValue('INDEX_MALL');
			$signUp = $this->model->getSignUp($malls[$index]->id->nombre, $malls[$index]->id->direccion);
			if(isset($signUp)){
				Session::setValue('SIGNUP',$signUp);
			}else{
				if(Session::issetValue('SIGNUP')){
					Session::removeValue('SIGNUP');
				}
			}
			$this->renderView('signUpValidation');	
		}
		function readSignUpTenant(){
			$signUp = new stdClass();
			$signUp->cuenta = $_POST['account'];
			$signUp->local = $_POST['place'];
			$signUp->centroComercial = $_POST['nameMall'];
			$signUp->direccionCentroComercial = $_POST['addressMall'];
			$signUp->clave = $_POST['password'];
			$signUp->nombres = $_POST['name'];
			$signUp->primerApellido = $_POST['firstname'];
			$signUp->segundoApellido = $_POST['surname'];

			$statusUpload = Uploads::tryUploadImage('profilePicture',explode('@',$_POST['account'])[0]);
			if(substr($statusUpload,0,5) === 'ERROR'){
				return $statusUpload;
			}
			$signUp->imagen = Image::imageToBase64(PATH_PICTURES . $statusUpload);
			
			$signUp->nombreLocal = $_POST['namePlace'];
			$signUp->horarioApertura = $_POST['horarioApertura'];
			$signUp->horarioCierre = $_POST['horarioCierre'];
			$signUp->telefono = $_POST['telefono'];
			$signUp->sitioWeb = $_POST['sitioWeb'];
			
			$statusUpload = Uploads::tryUploadImage('placePicture',$signUp->local);
			if(substr($statusUpload,0,5) === 'ERROR'){
				return $statusUpload;
			}
			$signUp->imagenLocal = Image::imageToBase64(PATH_PICTURES . $statusUpload);
			return $signUp;
		}
		function signUp(){
			Session::init();
			$signUp = $this->readSignUpTenant();	
			if(strcmp(gettype($signUp), 'string') !== 0){
				$this->model->insertSignUp($signUp);
				Session::setValue('ALERT',new Alert('alert-warning','Gracias por registrarse. Su registro fue enviado pronto un administrador lo validara'));
			}else{
				Session::setValue('ALERT',new Alert('alert-warning',$signUp));
			}
			$this->renderView('signUp');
		}
		function validate(){
			Session::init();
			if(isset($_POST['accept'])){
				$this->model->validateSignUp($_POST['account'], 'A');
				Session::setValue('ALERT',new Alert('alert-warning','Se acepto correctamente el registro'));
			}else{
				if(isset($_POST['refuse'])){
					$this->model->validateSignUp($_POST['account'], 'R');
					Session::setValue('ALERT',new Alert('alert-warning','Se rechazo correctamente el registro'));
				}
			}	
			$this->renderView('signUpValidation');
		}
		function getMalls(){
			header('Content-type: application/json; charset=utf-8');
			echo $this->model->getMalls();
		}
		function getPlacesFree(){
			header('Content-type: application/json; charset=utf-8');
			echo $this->model->getFreePlaces($_POST['nameMall'], $_POST['addressMall']);
		}
	}
?>
