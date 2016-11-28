<?php
	Session::init();
	$user = Session::getValue('USER');
	if(Session::issetValue('ALERT')){
		$alert = Session::getValue('ALERT');
	}
	$nameProfilePicture = Image::base64toImage($user->imagen, explode('@', $user->cuenta)[0], PATH_PICTURES);
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Perfil</title>
		<link rel="stylesheet" type = "text/css" href="<?php echo PATH_CSS; ?>bootstrap.min.css">
		<link rel="stylesheet" type = "text/css" href="<?php echo PATH_CSS; ?>formulario.css">
		<link rel="stylesheet" type = "text/css" href="<?php echo PATH_CSS; ?>main.css">
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
		<div class="container-fluit well">
			<div class="row">
				<h1 class="text-center text-capitalize">
					Mi Perfil
				</h1>
			</div>
		</div>
		<form class="container well" action="<?php echo URL . 'Profile_controller/modify'; ?>" method="POST" enctype="multipart/form-data">
			<div class="row marginTop30px">
					<div class="col-xs-12 col-sm-6 col-md-4 col-md-offset-4">
						<img alt="Foto de perfil" src="<?php echo URL . 'uploads/pictures/'.$nameProfilePicture; ?>" id="profilePicture"/>
					</div>
			</div>
			<div class="form-group">
				<label for="cuenta">Cuenta: </label>
				<input type="email" name="cuenta" class="form-control" id="cuenta" placeholder="Cuenta" maxlength="45" value="<?php echo $user->cuenta; ?> " readonly required/>
			</div>
			<div class="form-group">
				<label for="clave">Clave: </label>
				<input type="password" name="clave" class="form-control" id="clave" placeholder="Clave" maxlength="45" value="<?php echo $user->clave; ?>" required/>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="nombres">Nombres: </label>
					<input type="text" name="nombres" class="form-control" id="nombres" placeholder="Nombres" maxlength="30" value="<?php echo $user->nombres; ?>" required/>
				</div>
				<div class="col-xs-6">
					<label for="primerApellido">Primer apellido: </label>
					<input type="text" name="primerApellido" class="form-control" id="primerApellido" placeholder="Primer apellido" maxlength="30" value="<?php echo $user->primerApellido; ?>" required/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="segundoApellido">Segundo apellido: </label>
					<input type="text" name="segundoApellido" class="form-control" id="segundoApellido" placeholder="Segundo apellido" maxlength="30" value="<?php echo $user->segundoApellido; ?>" required/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="foto">Foto: </label>
					<input type="file" id="foto" name="foto" size="50" required>
					<p class="help-block">Seleccione su foto de perfil.</p>
				</div>
				<div class="col-xs-6">
					<input type="hidden" id="tipo" name="tipo" value = "<?echo $user->tipo; ?>" required>
				</div>
			</div>                
			<div class="row marginTop30px">                
				<div class="col-xs-6">
					<button type="submit" name="enviar">
						<img alt="Modificar area" src="<?php echo PATH_IMAGENS; ?>update.png" class="imagenCRUD"/>
					</button>
				</div>
			</div>
		</form>        
		<script type="text/javascript" src="<?php echo PATH_JS; ?>jquery.min.js"></script>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>bootstrap.min.js"></script>
	</body>
</html>
