<?php
	Session::init();
	$malls = Session::getValue('MALLS');
	$index = Session::getValue('INDEX_MALL');
	if(Session::issetValue('AREAS')){
		$areas = Session::getValue('AREAS');
	}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Areas</title>
		<link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS; ?>bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS; ?>formulario.css">
	</head>
	<body>
		<div class="container-fluit well">
			<div class="row text-center text-capitalize">
				<h1>
					Mis Areas 
				</h1>
				<h3><?php echo $malls[$index]->id->nombre; ?></h3><br/>
				<span><?php echo $malls[$index]->horarioApertura; ?> a <?php echo $malls[$index]->horarioCierre; ?></span>
			</div>
		</div>
		<?php
		if(!isset($areas)){
		?>
			<div class="alert alert-warning alert-dismissible" role="alert">
				<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<strong>INFORMACION</strong> Al parecer no existen areas registradas para este Centro Comercial
			</div>
		<?php	
		}else{
		?>
			<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
				<!-- Wrapper for slides -->
				<div class="carousel-inner" role="listbox">            
				<?php
				for($i=0; $i < count($areas); $i++){
				?>
					<div class="item <?php echo ($i == 0) ? 'active' : ''; ?>">
						<form class="container well" action="<?php echo URL . 'Areas_controller/delete'; ?>" method="POST" enctype="multipart/form-data">
							<div class="row marginTop30px">
								<div class="col-xs-12 col-sm-6 col-md-4 col-md-offset-4">
									<!-- <img alt="Foto del centro comercial" src="<php echo URL . 'uploads/pictures/'.$nameMallPicture; ?>" id="profilePicture"/> -->
								</div>
							</div>
							<div class="form-group">
								<label for="nombre">Nombre: </label>
								<input type="text" name="nombre" class="form-control" id="nombre" value="<? echo $areas[$i]->id->nombre;?>" maxlength="50" readonly required/>
							</div>
							<div class="row">
								<div class="col-xs-6">
									<label for="latitud">Latitud: </label>
									<input type="text" name="latitud" class="form-control" id="latitud" value="<? echo $areas[$i]->latitud; ?>" maxlength="7" readonly required/>
								</div>
								<div class="col-xs-6">
									<label for="longitud">Longitud: </label>
									<input type="text" name="longitud" class="form-control" id="longitud" value="<?php echo $areas[$i]->longitud; ?>" maxlength="7" readonly required/>
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
	</body>
</html>
