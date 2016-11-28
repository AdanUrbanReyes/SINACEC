<?php
/*	require 'config.php';//importamos el archivo config.php que contiene los defines de las librerias y urls
	require LIB.'Session.php';
	Session::init();
	//Session::setValue("USER","Ayan");
	print_r($_SESSION);
	//Session::destroy();
*/
	//http://php.net/manual/en/mysqli.overview.php
	//http://php.net/manual/es/function.db2-primary-keys.php
	require 'config.php';//importamos el archivo config.php que contiene los defines de las librerias y urls
	$url= (isset($_GET['url'])) ? $_GET['url'] : 'Index_controller/index';//si el .htaccess pudo atrapar la url la leemos si no le ponemos una por defaul (Index/index) cabe resaltar que el orden para la url es http://localhost/sinacec/Contolador/Metodo/Parametro	
	$url=explode('/',$url);//asemos el split de la url la cual solo contiene Controlador/Metodo/Parametros que es lo que atrapo nuestro .htaccess
	$controller=$url[0];
	$method=$url[1];
   spl_autoload_register(function($class){// se importan automaticamente las librerias
		if(file_exists(LIB.$class.'.php') ){
			require LIB.$class.'.php';
		}
	});
	$pathController='controller/'.$controller.'.php';//se asigana a una variable la POSIBLE ruta del controller
	if(file_exists($pathController)){// se pregunta si existe el controller que atrapo nuestro .htaccess
		require $pathController;// si existe controller se importa
		$controller=new $controller();// se crea controller
		if(method_exists($controller,$method)){// si el controller tiene un method llamado como nuestro .htaccess lo atrapo
			$controller->{$method}();//ejecutamos el method del controller sin enviar parametros siempre porque es el trabajo del metodo recorgerlos usando POST O GET
		}else{
			//aqui se maneja que pasa si no existe el method en el controller
		?>
			<script type="text/javascript">alert("Lo sentimos el Metodo solicitado no existe :(");</script>
		<?php
			//echo "ERROR el method ".$method." en el controller ".$pathController." no existe";
		}
	}else{
		//aqui se maneja que pasa si no existe el controller
	?>
		<script type="text/javascript">alert("Lo sentimos el Controlador solicitado no existe :(");</script>
	<?php
		//echo "ERROR el controller ".$pathController." no existe";
	}
?>
