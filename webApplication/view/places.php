<?php
	Session::init();
	$malls = Session::getValue('MALLS');
	$index = Session::getValue('INDEX_MALL');
	if(Session::issetValue('AREAS')){
		$areas = Session::getValue('AREAS');
	}
	if(Session::issetValue('ALERT')){
		$alert = Session::getValue('ALERT');
	}
	if(Session::issetValue('PLACE')){
		$place = Session::getValue('PLACE');
		$namePlacePicture = Image::base64toImage($place->imagen, $place->id->local, PATH_PICTURES);
	}
	$status = array();
	$status[0] = new stdClass();
	$status[0]->character = 'L';
	$status[0]->string = 'Libre';
	$status[1] = new stdClass();
	$status[1]->character = 'O';
	$status[1]->string = 'Ocupado';
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Locales</title>
		<link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS; ?>bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS; ?>formulario.css">
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
		<div class="row text-center text-capitalize">
			<h1>Administracion de Locales</h1>	
		</div>
		<form class="container well" action = "<?php echo URL . 'Places_controller/management'; ?>" method = "POST" enctype = "multipart/form-data">
			<div class="row marginTop30px">
				<div class="col-xs-12 col-sm-6 col-md-4 col-md-offset-4">
					<?php
					if(isset($namePlacePicture)){
					?>
						<img alt="Foto del local" src="<?php echo URL . 'uploads/pictures/'.$namePlacePicture; ?>" id="profilePicture"/>
					<?php
					}
					?>
				</div>
			</div>
			<div class="form-group">
				<label for="nameMall">Centro comercial: </label>
				<input type="text" name="nameMall" class="form-control" id="nameMall" value="<? echo $malls[$index]->id->nombre;?>" maxlength="70" readonly required/>
				<input type="hidden" name="addressMall" value="<?php echo $malls[$index]->id->direccion; ?>" readonly />
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="place">Local: </label>
					<input type="text" name="place" class="form-control" id="place" maxlength="11" value ="<?php echo isset($place) ? $place->id->local : '' ;?>" required>
					<p class="help-block">
						Normalmente esta compuesto por el numero del local seguido del piso en el que se encuentra.<br/>
						Ejemplos: 1025-PB, 1589-PP, 2548-SP, 3487-TP ...
					</p>
				</div>
				<div class="col-xs-6">
					<label for="area">Area: </label><br/>
					<select id="area" name="area" class="form-control" required>
						<?php
						if(isset($areas)){
							if(isset($place)){
								for($i=0; $i<count($areas); $i++){
									if(strcmp($areas[$i]->id->nombre, $place->area) === 0){
						?>
										<option value="<?php echo $areas[$i]->id->nombre; ?>" selected><?php echo $areas[$i]->id->nombre; ?></option>
						<?php
									}else{
						?>
										<option value="<?php echo $areas[$i]->id->nombre; ?>"><?php echo $areas[$i]->id->nombre; ?></option>
						<?php
									}
								}
							}else{
								for($i=0; $i < count($areas); $i++){
						?>							
									<option value="<?php echo $areas[$i]->id->nombre; ?>"><?php echo $areas[$i]->id->nombre; ?></option>
						<?php
								}
							}
						}
						?>
					</select>
				</div>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="status">Estatus: </label><br/>
					<select id="status" name="status" class="form-control" required>
						<?php
						if(isset($status)){
							if(isset($place)){
								for($i=0; $i<count($status); $i++){
									if(strcmp($status[$i]->character, $place->status) === 0){
						?>
										<option value="<?php echo $status[$i]->character; ?>" selected><?php echo $status[$i]->string; ?></option>
						<?php
									}else{
						?>
										<option value="<?php echo $status[$i]->character; ?>"> <?php echo $status[$i]->string; ?> </option>
						<?php
									}
								}
							}else{
								for($i=0; $i<count($status); $i++){
						?>
									<option value="<?php echo $status[$i]->character; ?>"> <?php echo $status[$i]->string; ?> </option>
						<?php
								}
							}
						}
						?>
					</select>
				</div>
				<div class="col-xs-6">
					<label for="picture">Foto: </label>
					<input type="file" id="picture" name="picture" required>
					<p class="help-block">Seleccione una foto para su local.</p>
				</div>
			</div>
			<div class="row marginTop30px">
				<div class="col-xs-6">
					<button type="submit" name="insert" onclick="setViewInsert()">
						<img alt="Insertar" src="<?php echo PATH_IMAGENS; ?>insert.png" class="imagenCRUD"/>
					</button>
					<button type="submit" name="search" onclick="setViewSearch()">
						<img alt="Buscar" src="<?php echo PATH_IMAGENS; ?>search.png" class="imagenCRUD"/>
					</button>
					<button type="submit" name="update" onclick="setViewUpdate()">
						<img alt="Modificar" src="<?php echo PATH_IMAGENS; ?>update.png" class="imagenCRUD"/>
					</button>
					<button type="submit" name="delete" onclick="setViewDelete()">
						<img alt="Eliminar" src="<?php echo PATH_IMAGENS; ?>delete.png" class="imagenCRUD"/>
					</button>
				</div>
			</div>
		</form>
		<?php
			Session::removeValue('PLACE');
		?>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>jquery.min.js"></script>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>bootstrap.min.js"></script>
		<script type="text/javascript" src="<?php echo PATH_JS; ?>places.js"></script>
	</body>
</html>
