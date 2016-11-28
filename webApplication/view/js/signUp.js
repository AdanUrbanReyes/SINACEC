var malls;
function setMalls(){
	var accion=$('#signUp').attr('action');
	var metodo=$('#signUp').attr('method');
	accion = accion.substring(0,accion.lastIndexOf('/')+1) + 'getMalls';
	//alert('accion = ' + accion + ' method = ' + metodo);
	$.ajax({
		url : accion,
		type : metodo,
		success : function(respuesta){
			//console.log('success = '+respuesta);
			respuesta.forEach(function(mall){
				//console.log(mall.id.nombre);
				$('#nameMall').append("<option value=\""+mall.id.nombre+"\">"+mall.id.nombre+"</option>");
			});
			malls = respuesta;
		},
		error : function(jqXHR,estado,error){
			console.log(estado);
		},
		complete : function(jqXHR,estado){
//			console.log('complete = '+estado);
		},
		timeout : 15000 //en milisegundos
	});
}
function setAddressMall(){
	$('#addressMall').empty();
	malls.forEach(function(mall){
		if($('#nameMall').val() == mall.id.nombre){
			$('#addressMall').append("<option value=\""+mall.id.direccion+"\">"+mall.id.direccion+"</option>");
		}
	});
}
function setPlaces(){
	$('#place').empty();
	var accion=$('#signUp').attr('action');
	var metodo=$('#signUp').attr('method');
	accion = accion.substring(0,accion.lastIndexOf('/')+1) + 'getPlacesFree';
	//alert('accion = ' + accion + ' method = ' + metodo);
	$.ajax({
		url : accion,
		type : metodo,
		data :  {nameMall : $('#nameMall').val(), addressMall : $('#addressMall').val()},
		success : function(respuesta){
			//console.log('success = '+respuesta);
			respuesta.forEach(function(place){
				//console.log(mall.id.nombre);
				$('#place').append("<option value=\""+place.id.local+"\">"+place.id.local+"</option>");
			});
		},
		error : function(jqXHR,estado,error){
			alert(error);
			console.log(estado);
		},
		complete : function(jqXHR,estado){
			alert(error);
//			console.log('complete = '+estado);
		},
		timeout : 15000 //en milisegundos
	});
}
