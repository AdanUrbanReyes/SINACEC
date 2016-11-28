<?php
	Session::init();
	$malls = Session::getValue('MALLS');
	$index = Session::getValue('INDEX_MALL');
	$address = Session::getValue('ADDRESS_MALL');
	$postalCode = Session::getValue('POSTAL_CODE_MALL');
	if(Session::issetValue('ALERT')){
		$alert = Session::getValue('ALERT');
	}
	$nameMallPicture = Image::base64toImage($malls[$index]->imagen, str_replace(' ','',$malls[$index]->id->nombre) . $malls[$index]->id->direccion, PATH_PICTURES);
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
                    Mi Centro Comercial
                </h1>
            </div>
        </div>
        <form class="container well" action="<?php echo URL . 'Mall_controller/modify'; ?>" method="POST" enctype="multipart/form-data">
			<div class="row marginTop30px">
					<div class="col-xs-12 col-sm-6 col-md-4 col-md-offset-4">
						<img alt="Foto del centro comercial" src="<?php echo URL . 'uploads/pictures/'.$nameMallPicture; ?>" id="profilePicture"/>
					</div>
			</div>
            <div class="form-group">
                <label for="nombre">Nombre: </label>
                <input type="text" name="nombre" class="form-control" id="nombre" value="<? echo $malls[$index]->id->nombre;?>" maxlength="100" readonly required/>
            </div>
            <!--Comienza informacion acerca de la direccion-->
				<div class="form-group">
					<input type="hidden" name="direccion" class="form-control" id="direccion" value="<? echo $address->id; ?>" readonly required/>
				</div>
            <div class="row">
                <div class="col-xs-6">
                    <label for="estado">Estado: </label>
                    <input type="text" name="estado" class="form-control" id="estado" value="<? echo $postalCode->estado; ?>" maxlength="30" readonly required/>
                </div>
                <div class="col-xs-6">
                    <label for="municipio">Municipio: </label>
                    <input type="text" name="municipio" class="form-control" id="municipio" value="<?php echo $postalCode->municipio; ?>" maxlength="30" readonly required/>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <label for="asentamiento">Asentamiento: </label>
                    <input type="text" name="asentamiento" class="form-control" id="asentamiento" value="<?php echo $postalCode->asentamiento; ?>" maxlength="30" readonly required/>
                </div>
                <div class="col-xs-6">
                    <label for="calle">Calle: </label>
                    <input type="text" name="calle" class="form-control" id="calle" value="<?php echo $address->calle; ?>" maxlength="30" readonly required/>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-6">
                    <label for="numeroExterior">Numero exterior: </label>
                    <input type="number" name="numeroExterior" class="form-control" id="numeroExterior" value=<?php echo $address->numeroExterior; ?> readonly required/>
                </div>
                <div class="col-xs-6">
                    <label for="codigoPostal">Codigo postal: </label>
                    <input type="number" name="codigoPostal" class="form-control" id="codigoPostal" value="<?php echo $postalCode->codigo; ?>" readonly required/>
                </div>
            </div>            
            <!--Termina informacion acerca de la direccion-->
            
            
            <div class="form-group">
                <label for="administrador">Administrador: </label>
                <input type="text" name="administrador" class="form-control" id="administrador" value="<?php echo $malls[$index]->administrador; ?>" maxlength="45" required/>
            </div>
            <div class="form-group">
                <label for="horarioApertura">Horario apertura: </label>
                <input type="time" name="horarioApertura" class="form-control" id="horarioApertura" value="<?php echo $malls[$index]->horarioApertura;?>" maxlength="13" required/>
            </div>
            <div class="form-group">
                <label for="horarioCierre">Horario cierre: </label>
                <input type="time" name="horarioCierre" class="form-control" id="horarioCierre" value="<?php echo $malls[$index]->horarioCierre;?>" maxlength="13" required/>
            </div>
            
            <div class="row">
                <div class="col-xs-6">
                    <label for="foto">Foto: </label>
                    <input type="file" id="foto" name="foto" required>
                    <p class="help-block">Seleccione su foto de perfil.</p>
                </div>
            </div>
            
            
            <div class="form-group">
                <label for="telefono">Telefono: </label>
                <input type="text" name="telefono" class="form-control" id="telefono" value="<?php echo $malls[$index]->telefono; ?>" maxlength="13"/>
            </div>
            <div class="form-group">
                <label for="sitioWeb">Sitio WEB: </label>
                <input type="text" name="sitioWeb" class="form-control" id="sitioWeb" value="<?php echo $malls[$index]->sitioWeb; ?>" maxlength="70" />
            </div>
            <div class="row margenSuperior30px">
                <div class="col-xs-6">
                    <button type="submit" name="submit">
                        <image alt="Modificar informacion" src="<? echo PATH_IMAGENS;?>update.png" class="imagenCRUD"/>
                    </button>
                </div>
            </div>
        </form>
       <script type="text/javascript" src="<? echo PATH_JS; ?>jquery.min.js"></script> 
       <script type="text/javascript" src="<? echo PATH_JS; ?>bootstrap.min.js"></script> 
    </body>
</html>
