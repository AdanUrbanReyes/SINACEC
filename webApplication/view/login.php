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
        <title>Inicio Sesion</title>
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
        <div class="container-fluit">
            <img alt="logotipo SINACEC" src="<?php echo PATH_IMAGENS; ?>edc2.jpg" class="img-responsive" id="logotipo"/>
            <h3 class="text-center foreColorSINACEC">
                Sistema de navegacion dentro de centros comerciales a traves de un dispositivo con sistema operativo android
            </h3>
            <h4 class="text-center text-capitalize welcomeForeColor marginTop70px">
                Bienvenido a SINACEC
            </h4>
        </div>
        <div class="container well" id="login">
            <div class="row">
                <div class="col-xs-12">
                    <img alt="avatar" src="<?php echo PATH_IMAGENS; ?>avatar.png" class="img-responsive" id="avatar"/>
                </div>
            </div>
            <form class="login" action="<?php echo URL; ?>Login_controller/signIn" method="POST">
                <div class="form-group">
                    <label for="cuenta">Cuenta</label>
                    <input type="email" name="cuenta" class="form-control" id="cuenta" placeholder="Cuenta" maxlength="45" required/>
                </div>
                <div class="form-group">
                    <label for="clave">Clave</label>
                    <input type="password" name="clave" class="form-control" id="clave" placeholder="Clave" maxlength="45" required/>
                </div>
                <button type="submit" class="btn btn-lg btn-primary btn-block">Iniciar</button>
            </form>
            <p class="help-block"><a href="<?php echo URL; ?>SignUp_controller/fillSignUp">No estas registrado?</a></p>
        </div>
        <script type="text/javascript" src="<?php echo PATH_JS; ?>jquery.min.js"></script>
        <script type="text/javascript" src="<?php echo PATH_JS; ?>bootstrap.min.js"></script>
    </body>
</html>
