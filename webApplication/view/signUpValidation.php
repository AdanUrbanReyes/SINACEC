<?php
	Session::init();
	if(Session::issetValue('SIGNUP')){
		$signUp = Session::getValue('SIGNUP');
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
		if(!isset($signUp)){
		?>
			<div class="alert alert-warning alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<strong>INFORMACION</strong> Al parecer no existen registros pendientes para validar en este centro comercial
			</div>
		<?php
		}else{
		?>
			<div class="row text-center text-capitalize">
				<h1>Registros pendientes por validar</h1>
			</div>
			<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">            
				<?php
				for($i=0; $i < count($signUp); $i++){
				?>
					<div class="item <?php echo ($i == 0) ? 'active' : ''; ?>"><!-- zf71j for folding lines of form-->
						<form id="signUp" class="container well" action = "<?php echo URL . 'SignUp_controller/validate'; ?>" method = "POST">
							<div class="form-group">
								<!-- <input type="hidden" name="index" class="form-control" id="index" value="<php echo $i; ?>" readonly/> -->
								<label for="account">Cuenta: </label>
								<input type="email" name="account" class="form-control" id="account" value="<?php echo $signUp[$i]->cuenta ; ?>" readonly/>
							</div>
							<div class="form-group">
								<label for="password">Clave: </label>
								<input type="password" name="password" class="form-control" id="password" value="<?php echo $signUp[$i]->clave ; ?>" readonly/>
							</div>
							<div class="row">
								<div class="col-xs-6">
									<label for="name">Nombres: </label>
									<input type="text" name="name" class="form-control" id="name" value="<?php echo $signUp[$i]->nombres ; ?>" readonly/>
								</div>
								<div class="col-xs-6">
									<label for="firstname">Primer apellido: </label>
									<input type="text" name="firstname" class="form-control" id="firstname" value="<?php echo $signUp[$i]->primerApellido ; ?>" readonly/>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-6">
									<label for="surname">Segundo apellido: </label>
									<input type="text" name="surname" class="form-control" id="surname" value="<?php echo $signUp[$i]->segundoApellido ; ?>" readonly/>
								</div>
							</div>
							<!--Start information for Local-->	
							<div class="form-group">
								<label for="nameMall">Centro Comercial: </label>
								<input type="text" name="nameMall" class="form-control" id="nameMall" value="<?php echo $signUp[$i]->centroComercial ; ?>" readonly/>
							</div>
							<div class="row">
								<div class="col-xs-6">
									<label for="addressMall">Direccion del Centro Comercial: </label>
									<input type="text" name="nameMall" class="form-control" id="nameMall" value="<?php echo $signUp[$i]->direccionCentroComercial ; ?>" readonly/>
								</div>
								<div class="col-xs-6">
									<label for="place">Local: </label>
									<input type="text" name="nameMall" class="form-control" id="nameMall" value="<?php echo $signUp[$i]->local ; ?>" readonly/>
								</div>
							</div>
							<div class="form-group">
								<label for="namePlace">Nombre del Local: </label>
								<input type="text" name="namePlace" class="form-control" id="namePlace" value="<?php echo $signUp[$i]->nombreLocal ; ?>" readonly/>
							</div>
							<div class="row">
								<div class="col-xs-6">
									<label for="horarioApertura">Horario apertura: </label>
									<input type="time" name="horarioApertura" class="form-control" id="horarioApertura" value="<?php echo $signUp[$i]->horarioApertura ; ?>" readonly/>
								</div>
								<div class="col-xs-6">
									<label for="horarioCierre">Horario cierre: </label>
									<input type="time" name="horarioCierre" class="form-control" id="horarioCierre" value="<?php echo $signUp[$i]->horarioCierre ; ?>" readonly/>
								</div>
							</div>
							<div class="form-group">
								<label for="telefono">Telefono: </label>
								<input type="text" name="telefono" class="form-control" id="telefono" value="<?php echo $signUp[$i]->telefono ; ?>" readonly/>
							</div>
							<div class="form-group">
								<label for="sitioWeb">Sitio WEB: </label>
								<input type="text" name="sitioWeb" class="form-control" id="sitioWeb" value="<?php echo $signUp[$i]->sitioWeb ; ?>" readonly/>
							</div>
							<div class="row">
								<div class="col-xs-6">
         						<button type="submit" name="accept" class="btn btn-lg btn-primary btn-block">Aceptar</button>
								</div>
								<div class="col-xs-6">
   	      					<button type="submit" name="refuse" class="btn btn-lg btn-danger btn-block">Rechazar</button>
								</div>
							</div>	
						</form>
					</div>
				<?php
				}
				?>
				</div>
				<!-- Controls -->
				<a id="botonAnterior" class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
					<span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
					<span class="sr-only">Anterior</span>
				</a>
				<a id="botonSiguiente" class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
					<span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
					<span class="sr-only">Siguiente</span>
				</a>
			</div>
		<?php
		}
		?>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>jquery.min.js"></script>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>bootstrap.min.js"></script>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>signUp.js"></script>
	</body>
</html>
