<?php
	class Areas_controller{
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
		function areas(){
			$this->renderView('areas');
		}
	}
?>
