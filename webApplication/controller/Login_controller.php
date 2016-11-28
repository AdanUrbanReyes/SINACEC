<?php
	class Login_controller{
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
		function login(){
			Session::init();
			if(Session::issetValue('USER')){
				$this->renderView('main');
			}else{
				$this->renderView('login');
			}
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
		function signIn(){
			Session::init();
			$user = $this->model->validateSignIn($_POST['cuenta'], $_POST['clave']);
			if( isset($user) ){
				Session::setValue('USER',$user);
				if($user->tipo === 'C'){
					$malls = $this->model->getMalls($user->cuenta);
					// here we can validate if there are malls with a if(isset($malls))
					Session::setValue('MALLS', $malls);
					if(count($malls) > 1){
						$this->renderView('selectionMall');
					}else{
						if(count($malls) == 1){
							Session::setValue('INDEX_MALL',0);
							$this->setInformationMall($malls[0]);
							$this->renderView('main');
						}
					}
				}else{
					$places = $this->model->getPlaces($user->cuenta);
					// here we can validate if there are places with a if(isset($places))
					Session::setValue('RENTED_PLACES',$places);
					if(count($places) > 1){
						$this->renderView('selectionRentedPlace');
					}else{
						if(count($places) == 1){
							Session::setValue('INDEX_RENTED_PLACE',0);
							$this->renderView('main');
						}
					}
				}
			}else{
				Session::setValue('ALERT',new Alert('alert-warning','Cuenta y/o Clave incorrectos'));
				$this->renderView('login');
			}
		}
	}
?>
