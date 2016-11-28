<?php
	Session::init();
	$places = Session::getValue('RENTED_PLACES');
	$index = Session::getValue('INDEX_RENTED_PLACE');
	if(Session::issetValue('ALERT')){
		$alert = Session::getValue('ALERT');
	}
	if(Session::issetValue('PRODUCT')){
		$product = Session::getValue('PRODUCT');
		$nameProductPicture = Image::base64toImage($product->imagen, $product->id->nombre, PATH_PICTURES);
	}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Productos</title>
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
					Mis Productos
				</h1>
			</div>
		</div>
		<form class="container well" action="<?php echo URL . 'Products_controller/management'; ?>" method="POST" enctype="multipart/form-data">
			<div class="row marginTop30px">
				<div class="col-xs-12 col-sm-6 col-md-4 col-md-offset-4">
					<?php
						if(isset($nameProductPicture)){
					?>
					<img alt="Imagen del Producto" src="<?php echo URL . 'uploads/pictures/'.$nameProductPicture; ?>" id="productPicture"/>
					<?php
					}
					?>
				</div>
			</div>
			<div class="form-group">
				<label for="mall">Centro comercial: </label>
				<input type="text" name="mall" class="form-control" id="mall" value="<?php echo $places[$index]->id->centroComercial; ?>" maxlength="70" readonly required/>
				<input type="hidden" name="addressMall" class="form-control" id="addressMall" value="<?php echo $places[$index]->id->direccionCentroComercial; ?>" maxlength="10" readonly required/>
			</div>                            
			<div class="row">
				<div class="col-xs-2">
					<label for="place">Local: </label>
					<input type="text" name="place" class="form-control" id="place" value="<?php echo $places[$index]->id->local; ?>" maxlength="11" readonly required/>
				</div>
				<div class="col-xs-10">
					<label for="nameProduct">Nombre del producto: </label>
					<input type="text" name="nameProduct" class="form-control" id="nameProduct" maxlength="30" value="<?php echo (isset($product)) ? $product->id->nombre : ''; ?>" placeholder="Nombre del producto" required/>
				</div>
			</div>
			<div class="form-group">
				<label for="description">Descripcion: </label>
				<textarea name="description" class="form-control" id="description" placeholder="Descripcion" required ><?php echo (isset($product)) ? $product->descripcion : ''; ?></textarea>
			</div>
			<div class="row">
				<div class="col-xs-6">
					<label for="image">Imagen: </label>
					<input type="file" id="picture" name="picture" required>
					<p class="help-block">Seleccione una imagen para su producto.</p>
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
			Session::removeValue('PRODUCT');
		?>
		<script type="text/javascript" src="<? echo PATH_JS; ?>jquery.min.js"></script> 
		<script type="text/javascript" src="<? echo PATH_JS; ?>bootstrap.min.js"></script> 
		<script type="text/javascript" src="<?php echo PATH_JS; ?>products.js"></script>
	</body>
</html>
