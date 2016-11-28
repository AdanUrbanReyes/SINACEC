<?php
	Session::init();
	$places = Session::getValue('RENTED_PLACES');
	$indexMall = (Session::issetValue('INDEX_RENTED_PLACE')) ? Session::getValue('INDEX_RENTED_PLACE') : 0;
?>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Seleccion Local</title>
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
                    Mis Locales
                </h1>
                
                <h4 class="text-center marginTop70px">
					 	Por favor seleccione alguno de los <?php echo count($places); ?> locales con el que se trabajara
                </h4>
            </div>
        </div>
        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
            <!-- Wrapper for slides -->
            <div class="carousel-inner" role="listbox">            
					<?php
					for($i=0;$i < count($places); $i++){
					?>
                <div class="item <?php echo ($i == $indexPlace) ? 'active' : ''; ?>">
                    <form class="container well <?php echo ($i == $indexPlace) ? 'selectedRentedPlace' : ''; ?>" action ="<?php echo URL.'RentedPlace_controller/select'?>" method="POST">
                        <input type="number" name="indexRentedPlace" value="<?php echo $i; ?>" hidden required/>
                        <div class="form-group">
                            <label for="mall">Centro comercial: </label>
                            <input type="text" name="mall" class="form-control" id="mall" value="<?php echo $places[$i]->id->centroComercial; ?>" maxlength="70" readonly/>
                        </div>                            
                        <div class="row">
                            <div class="col-xs-2">
                                <label for="place">Local: </label>
                                <input type="text" name="place" class="form-control" id="place" value="<?php echo $places[$i]->id->local; ?>" maxlength="11" readonly />
                            </div>
                            <div class="col-xs-10">
                                <label for="name">Nombre: </label>
                                <input type="text" name="name" class="form-control" id="name" value="<?php echo $places[$i]->nombre; ?>" maxlength="50" readonly/>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <label for="openingHours">Horario apertura: </label>
                                <input type="time" name="openingHours" class="form-control" id="openingHours" value="<?php echo $places[$i]->horarioApertura; ?>" maxlength="12" readonly />
                            </div>
                            <div class="col-xs-6">
                                <label for="closingHours">Horario cierre: </label>
                                <input type="time" name="closingHours" class="form-control" id="closingHours" value="<?php echo $places[$i]->horarioCierre; ?>" maxlength="12" readonly />
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="webSide">Sitio WEB: </label>
                            <input type="text" name="webSide" class="form-control" id="webSide" value="<?php echo $places[$i]->sitioWeb; ?>" maxlength="70" readonly />
                        </div>
                        <div class="row">
                            <div class="col-xs-6">
                                <label for="phone">Telefono: </label>
                                <input type="text" name="phone" class="form-control" id="phone" value="<?php echo $places[$i]->telefono; ?>" maxlength="13" readonly />
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
