<?php
	Session::init();
	$user = Session::getValue('USER');
	//May be are missing some validations :S
	if($user->tipo === 'C'){
		$malls = Session::getValue('MALLS');
		$index = Session::getValue('INDEX_MALL');
	}else{
		$places = Session::getValue('RENTED_PLACES');
		$index = Session::getValue('INDEX_RENTED_PLACE');
	}
	$nameProfilePicture = Image::base64toImage($user->imagen, explode('@', $user->cuenta)[0], PATH_PICTURES);
	if(Session::issetValue('ALERT')){
		$alert = Session::getValue('ALERT');
	}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Bienvenido</title>
		<link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS; ?>bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS; ?>main.css">
	</head>
	<body>
		<?php
		if(isset($alert)){
		?>
				<div class="alert <?php echo $alert->type; ?> alert-dismissible" role="alert">
					<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<strong>INFORMACION</strong> <?php echo $alert->message; ?>
            </div>
		<?php	
			Session::removeValue('ALERT');
			}
		?>
		<header>
			<div class="container-fluid">
				<div class="row well">
					<div class="col-xs-12 col-sm-6 col-md-4">
						<img alt="Foto de perfil" src="<?php echo URL . 'uploads/pictures/'.$nameProfilePicture; ?>" id="profilePicture"/>
					</div>
					<div class="clearfix visible-xs"></div>
					<div class="col-xs-12 col-sm-6 col-md-8">
						<div class="row">       
							<h1 class="text-center">Bienvenido</h1>
						</div>
						<div class="row well">
							<h3>
								<?php echo $user->nombres.' '.$user->primerApellido.' '.$user->segundoApellido; ?>
							</h3><br/>
							<span>
								<?php echo $user->cuenta; ?>
							</span>
						</div>
						<div class="row well">
							<?php 
								if($user->tipo === 'C'){
							?>
									<h3><?php echo $malls[$index]->id->nombre; ?></h3><br/>
									<span><?php echo $malls[$index]->horarioApertura; ?> a <?php echo $malls[$index]->horarioCierre; ?></span>
							<?php	
								}else{
							?>
									<h3><?php echo $places[$index]->nombre; ?></h3><br/>
									<span><?php echo $places[$index]->horarioApertura; ?> a <?php echo $places[$index]->horarioCierre; ?></span>
							<?php
								}
							?>
						</div>                            
						<div class="row">
							<div class="col-xs-12 col-sm-6 col-md-4 col-lg-4">
								<form action = "<?php echo URL; ?>Main_controller/closeSession" method="POST">
									<button type="submit" class="btn btn-danger btn-block">Cerrar Sesion</button>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</header>
		<section>
			<div  class="text-center">
				<h1>Menu</h1>
				<p>
					Estas son las opciones de menu que actualmente tiene el Sistema SINACEC puede elejir cualquiera.<br/>
					<span class="colorNotas">NOTA: En la aplicacion para moviles podra encontrar mas opciones del menu.</span>
				</p>
				<?php
					if($user->tipo === 'C'){
						$pathModules = PATH_MODULES_MANAGER;
						$pathImagensModules = PATH_IMAGENS_MODULES_MANAGER;
					}else{
						$pathModules = PATH_MODULES_TENANT ;
						$pathImagensModules = PATH_IMAGENS_MODULES_TENANT;
					} 
					$handle = fopen($pathModules,'r');
					if($handle){
						while( ($line = fgets($handle)) !== false){
							$module = json_decode($line);
				?>
							<div class="col-md-3 inlineBlock">
								<article>
									<a href="<?php echo URL . $module->link; ?>">
										<img alt="<?php echo $module->name; ?>" src="<?php echo $pathImagensModules . $module->image; ?>" class="col-md-4 quitarFloat"/>
										<p><?php echo $module->name; ?></p>
									</a>
								</article>
							</div>
				<?
						}
						fclose($handle);
					}else{
				?>
						<h1>NO SE ENCONTRO EL ARCHIVO <?php echo $pathModules; ?> POR LO TANTO NO SE PUDIERON CARGAR LOS MODULOS</h1>
				<?php
					}
				?>
			</div>
		</section>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>jquery.min.js"></script>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>bootstrap.min.js"></script>
	</body>
</html>
