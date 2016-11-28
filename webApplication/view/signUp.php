<?php
	Session::init();
	if(Session::issetValue('ALERT')){
		$alert = Session::getValue('ALERT');
	}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Registro</title>
		<link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS; ?>bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS; ?>formulario.css">
	</head>
	<body onload="setMalls()">
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
		<div class="row text-center text-capitalize">
			<h1>Unete SINACEC</h1>
			<span>La mejor forma de navegar dentro de un centro comercial</span>
		</div>
		<form id="signUp" class="container well" action = "<?php echo URL . 'SignUp_controller/signUp'; ?>" method = "POST" enctype = "multipart/form-data">
			<div class="form-group">
				<label for="account">Cuenta: </label>
				<input type="email" name="account" class="form-control" id="account" placeholder="Cuenta" maxlength="45" required/>
			</div>
			<div class="form-group">
				<label for="password">Clave: </label>
				<input type="password" name="password" class="form-control" id="password" placeholder="Clave" maxlength="45" required/>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="name">Nombres: </label>
					<input type="text" name="name" class="form-control" id="name" placeholder="Nombres" maxlength="30" required/>
				</div>
				<div class="col-xs-6">
					<label for="firstname">Primer apellido: </label>
					<input type="text" name="firstname" class="form-control" id="firstname" placeholder="Primer apellido" maxlength="30" required/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="surname">Segundo apellido: </label>
					<input type="text" name="surname" class="form-control" id="surname" placeholder="Segundo apellido" maxlength="30" required/>
				</div>
				<div class="col-xs-6">
					<label for="profilePicture">Foto Perfil: </label>
					<input type="file" id="profilePicture" name="profilePicture" size="50" required>
					<p class="help-block">Seleccione su foto de perfil.</p>
				</div>
			</div>
			<!--Start information for Local-->	
			<div class="form-group">
				<label for="nameMall">Centro Comercial: </label>
				<select id="nameMall" name="nameMall" class="form-control" onblur="setAddressMall()" required>

				</select>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="addressMall">Direccion del Centro Comercial: </label>
					<select id="addressMall" name="addressMall" class="form-control" onblur="setPlaces()" required>
						
					</select>
				</div>
				<div class="col-xs-6">
					<label for="place">Local: </label>
					<select id="place" name="place" class="form-control" required>
						
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="namePlace">Nombre del Local: </label>
				<input type="text" name="namePlace" class="form-control" id="namePlace" placeholder="Nombre del Local" maxlength="50" required/>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="horarioApertura">Horario apertura: </label>
					<input type="time" name="horarioApertura" class="form-control" id="horarioApertura" placeholder="Horario apertura" maxlength="13" required/>
				</div>
				<div class="col-xs-6">
					<label for="horarioCierre">Horario cierre: </label>
					<input type="time" name="horarioCierre" class="form-control" id="horarioCierre" placeholder="Horario cierre"  maxlength="13" required/>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="placePicture">Foto del Local: </label>
					<input type="file" id="placePicture" name="placePicture" required>
					<p class="help-block">Seleccione la foto del Local.</p>
				</div>
			</div>
			<div class="form-group">
				<label for="telefono">Telefono: </label>
				<input type="text" name="telefono" class="form-control" id="telefono" placeholder="Telefono" maxlength="13"/>
			</div>
			<div class="form-group">
				<label for="sitioWeb">Sitio WEB: </label>
				<input type="text" name="sitioWeb" class="form-control" id="sitioWeb" placeholder="Sitio WEB" maxlength="70" />
			</div>
         <button type="submit" class="btn btn-lg btn-primary btn-block">Enviar</button>
		</form>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>jquery.min.js"></script>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>bootstrap.min.js"></script>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>signUp.js"></script>
	</body>
</html>
