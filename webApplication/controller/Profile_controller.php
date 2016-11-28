<?php
	class Profile_controller{
		function __construct(){
			$this->loadModel();
		}
		function renderView($view){
			$pathView = PATH_VIEW . $view. '.php';
			if(file_exists($pathView)){
				require $pathView;
			}
		}
		function loadModel(){
			$model = str_replace('_controller','_model',get_class($this));
			$pathModel = PATH_MODEL . $model . '.php';
			if(file_exists($pathModel)){
				require $pathModel;
				$this->model = new $model;
			}
		}
		function profile(){
			$this->renderView('profile');
		}
		function readUser(){
			$user = new stdClass();
			$user->cuenta = $_POST['cuenta'];
			$user->clave = $_POST['clave'];
			$user->nombres = $_POST['nombres'];
			$user->primerApellido = $_POST['primerApellido'];
			$user->segundoApellido = $_POST['segundoApellido'];
			$statusUpload = Uploads::tryUploadImage('foto', explode('@',$_POST['cuenta'])[0]);
			if(substr($statusUpload,0,5) === 'ERROR'){
				return $statusUpload;
			}
			$user->imagen = Image::imageToBase64(PATH_PICTURES . $statusUpload);
			$user->tipo = $_POST['tipo'];
			return $user;
		}
		function modify(){
			Session::init();
			if(isset($_POST['enviar'])) {
				$user = $this->readUser();
				if(strcmp(gettype($user), 'string') !== 0){
					$response = $this->model->update($user);
					// validate reponse is missing 
					Session::setValue('USER', $user);
					Session::setValue('ALERT',new Alert('alert-warning','Usuario modificado'));
				}else{
					Session::setValue('ALERT',new Alert('alert-warning', $user));
				}
			}else{
				Session::setValue('ALERT',new Alert('alert-warning','No se a presionado el boton de modificar'));
			}
			$this->renderView('main');
		}
	}
?>
