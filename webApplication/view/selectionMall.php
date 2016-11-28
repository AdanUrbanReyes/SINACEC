<?php
	Session::init();
	$malls = Session::getValue('MALLS');
	$indexMall = (Session::issetValue('INDEX_MALL')) ? Session::getValue('INDEX_MALL') : 0;
?>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Seleccion Centro Comercial</title>
		  <link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS ?>bootstrap.min.css">
       <!--
		 	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous" type="text/css">
		 -->
        <link rel="stylesheet" type="text/css" href="<?php echo PATH_CSS; ?>formulario.css">
    </head>
    <body>
        <div class="container-fluit well">
            <div class="row">
                <h1 class="text-center text-capitalize">
                    Mis Centros Comerciales
                </h1>
                
                <h4 class="text-center marginTop70px">
                    Por favor seleccione alguno de los <?php echo count($malls); ?> centro comercial con el que se trabajara 
                </h4>
            </div>
        </div>
        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">            
                <?php
                for($i=0; $i < count($malls); $i++){
                ?>
                <div class="item <?php echo ($i == $indexMall) ? 'active' : ''; ?>">
                    <form class="container well <?php echo ($i == $indexMall) ? 'selectedMall' : ''; ?>" action ="<?php echo URL.'Mall_controller/select'?>" method="POST">
                        <input type="number" name="indexMall" value="<?php echo $i; ?>" hidden required/>
                        <div class="form-group">
                            <label for="nombre">Nombre: </label>
                            <input type="text" name="nombre" class="form-control" id="nombre" value="<?php echo $malls[$i]->id->nombre; ?>" maxlength="70" disabled />
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <label for="horarioApertura">Horario apertura: </label>
                                <input type="time" name="horarioApertura" class="form-control" id="horarioApertura" value="<?php echo $malls[$i]->horarioApertura; ?>" maxlength="12" disabled/>
                            </div>
                            <div class="col-xs-6">
                                <label for="horarioCierre">Horario cierre: </label>
                                <input type="time" name="horarioCierre" class="form-control" id="horarioCierre" value="<?php echo $malls[$i]->horarioCierre; ?>" maxlength="12" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="sitioWEB">Sitio WEB: </label>
                            <input type="text" name="sitioWEB" class="form-control" id="sitioWEB" value="<?php echo $malls[$i]->sitioWeb; ?>" maxlength="70" disabled />
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <label for="telefono">Telefono: </label>
                                <input type="time" name="telefono" class="form-control" id="telefono" value="<?php echo $malls[$i]->telefono; ?>" maxlength="13" disabled/>
                            </div>
                        </div>                                                  
                        <div class="row marginTop30px">
                            <div class="col-xs-6">
                                <button type="submit" name="seleccionar">
                                    <img alt="seleccionar" src="<?php echo PATH_IMAGENS; ?>seleccionar.png" class="imagenCRUD"/>
                                </button>
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
           
        <script type="text/javascript" src="<?php echo PATH_JS; ?>jquery.min.js"></script>
        <script type="text/javascript" src="<?php echo PATH_JS; ?>bootstrap.min.js"></script>
    </body>
</html>
