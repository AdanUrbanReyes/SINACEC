function setViewInsert(){
	$("#picture").prop('required',true);
	$("#description").prop('required',true);
}
function setViewSearch(){
	$("#picture").prop('required',null);
	$("#description").prop('required',null);
	
}
function setViewUpdate(){
	$("#picture").prop('required',true);
	$("#description").prop('required',true);
}
function setViewDelete(){
	$("#picture").prop('required',null);
	$("#description").prop('required',null);
}
