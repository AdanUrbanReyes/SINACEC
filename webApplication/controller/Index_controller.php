<?php
	class Index_controller{
		function __construct(){
			//Session::init();
			//here can init url of web service
		}
		function renderView($view){
			$pathView=PATH_VIEW.$view.'.php';
			if(file_exists($pathView)){
				require $pathView; 
			}
		}
		function index(){
			$this->renderView('index');
		}
	}
?>
