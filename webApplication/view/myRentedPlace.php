<?php
	Session::init();
	$places = Session::getValue('RENTED_PLACES');
	$index = Session::getValue('INDEX_RENTED_PLACE');
	if(Session::issetValue('ALERT')){
		$alert = Session::getValue('ALERT');
	}
	$namePlacePicture = Image::base64toImage($places[$index]->imagen, $places[$index]->id->local, PATH_PICTURES);
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Centro Comercial</title>
		<link rel="stylesheet" type="text/css" href="<? echo PATH_CSS; ?>bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="<? echo PATH_CSS; ?>main.css">
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
					Mi Local
				</h1>
			</div>
		</div>
		<form class="container well" action="<?php echo URL . 'RentedPlace_controller/updateRentedPlace'; ?>" method="POST" enctype="multipart/form-data">
			<div class="row marginTop30px">
				<div class="col-xs-12 col-sm-6 col-md-4 col-md-offset-4">
					<img alt="Foto del centro comercial" src="<?php echo URL . 'uploads/pictures/'.$namePlacePicture; ?>" id="profilePicture"/>
				</div>
			</div>
			<div class="form-group">
				<label for="mall">Centro comercial: </label>
				<input type="text" name="mall" class="form-control" id="mall" value="<?php echo $places[$index]->id->centroComercial; ?>" maxlength="70" readonly/>
				<input type="hidden" name="addressMall" class="form-control" id="addressMall" value="<?php echo $places[$index]->id->direccionCentroComercial; ?>" maxlength="10" readonly/>
			</div>                            
			<div class="row">
				<div class="col-xs-2">
					<label for="place">Local: </label>
					<input type="text" name="place" class="form-control" id="place" value="<?php echo $places[$index]->id->local; ?>" maxlength="11" readonly required/>
				</div>
				<div class="col-xs-10">
					<label for="name">Nombre: </label>
					<input type="text" name="name" class="form-control" id="name" value="<?php echo $places[$index]->nombre; ?>" maxlength="50" required/>
				</div>
			</div>
			<div class="form-group">
				<label for="tenant">Locatario: </label>
				<input type="text" name="tenant" class="form-control" id="tenant" value="<?php echo $places[$index]->locatario; ?>" maxlength="45" required />
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="openingHours">Horario apertura: </label>
					<input type="time" name="openingHours" class="form-control" id="openingHours" value="<?php echo $places[$index]->horarioApertura; ?>" maxlength="12" required />
				</div>
				<div class="col-xs-6">
					<label for="closingHours">Horario cierre: </label>
					<input type="time" name="closingHours" class="form-control" id="closingHours" value="<?php echo $places[$index]->horarioCierre; ?>" maxlength="12" required />
				</div>
			</div>
			<div class="form-group">
				<label for="webSide">Sitio WEB: </label>
				<input type="text" name="webSide" class="form-control" id="webSide" value="<?php echo $places[$index]->sitioWeb; ?>" maxlength="70" required />
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="phone">Telefono: </label>
					<input type="text" name="phone" class="form-control" id="phone" value="<?php echo $places[$index]->telefono; ?>" maxlength="13" required />
				</div>
				<div class="col-xs-6">
					<label for="picture">Foto: </label>
					<input type="file" id="picture" name="picture" required>
					<p class="help-block">Seleccione una foto para su local.</p>
				</div>
			</div>
			<div class="row marginTop30px">
				<div class="col-xs-6">
					<button type="submit" name="submit">
						<img alt="Modificar informacion" src="<? echo PATH_IMAGENS;?>update.png" class="imagenCRUD"/>
					</button>
				</div>
			</div>
		</form>
		<script type="text/javascript" src="<? echo PATH_JS; ?>jquery.min.js"></script> 
		<script type="text/javascript" src="<? echo PATH_JS; ?>bootstrap.min.js"></script> 
	</body>
</html>
