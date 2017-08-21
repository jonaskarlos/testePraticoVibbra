//JAVASCRIPT

function formataCpf(campo, teclapres) {
	var tecla = teclapres.keyCode;
	var vr = new String(campo.value);
	vr = vr.replace(".", "");
	vr = vr.replace("/", "");
	vr = vr.replace("-", "");
	tam = vr.length + 1;
	if (tecla != 14) {
		if (tam == 4)
			campo.value = vr.substr(0, 3) + '.';
		if (tam == 7)
			campo.value = vr.substr(0, 3) + '.' + vr.substr(3, 6) + '.';
		if (tam == 11)
			campo.value = vr.substr(0, 3) + '.' + vr.substr(3, 3) + '.'
					+ vr.substr(7, 3) + '-' + vr.substr(11, 2);
	}
}

function mascaraHora(edit) {
	if (event.keyCode < 48 || event.keyCode > 57) {
		event.returnValue = false;
	}
	if (edit.value.length == 2 || edit.value.length == 5) {
		edit.value += ":";
	}
}

function formataQtdHoraMinuto(edit) {
	var horas, minutos, result, len, caracter;
	if (event.keyCode != 8) {
		if (event.keyCode < 48 || event.keyCode > 57) {
			event.returnValue = false;
		}
	}
	result = edit.value;
	//alert("inicio:" + result);
	result = retiraZerosEsquerda(limpaTextoNumerico(edit.value)).substr(0, 4);
	//alert("limpa:" + result);
	result = preencheZerosEsquerda(result, 4);
	//alert(result);
	horas = result.substr(0, result.length - 1);
	minutos = result.substr(result.length - 1);
	result = horas + ":" + minutos;
	//alert(result);
	edit.value = result;
} 

function preencheZerosEsquerda(texto, len) {
	while (texto.length < len) {
		texto = "0" + texto;
	}
	return texto;
}

function retiraZerosEsquerda(texto) {
	while (texto.length  > 0 && texto.charAt(0) == '0') {
		texto = texto.substr(1);
	}
	return texto;
}

function limpaTextoNumerico(texto) {
	var numeros = "0123456789";
	var cont = 0;
	while (cont < texto.length) {
		if (numeros.indexOf(texto.charAt(cont)) < 0)
			texto = texto.substr(0, cont) + texto.substr(cont + 1);
		else
			cont++;
	}
	return texto;
}

function somenteNumero(e) {
	var tecla = (window.event) ? event.keyCode : e.which;

	if ((tecla > 47 && tecla < 58))
		return true;
	else {
		if (tecla != 8 && tecla != 0)
			return false;
		else
			return true;
	}
}


//function somenteNumeroData(inputData, e) {
function somenteNumeroData(e) {
	//var data = inputData.value;
	//alert(inputData.value);
	var tecla = (window.event) ? event.keyCode : e.which;

	if ((tecla > 47 && tecla < 58))
		return true;
	else {
		if(tecla == 47 || tecla == 92 )
			return true;
		if (tecla != 8 && tecla != 0)
			return false;
		else
			return true;
	}
}

function BlockKeybord() {
	if ((event.keyCode < 48) || (event.keyCode > 57)) {
		event.returnValue = false;
	}
}

function troca(str, strsai, strentra) {
	while (str.indexOf(strsai) > -1) {
		str = str.replace(strsai, strentra);
	}
	return str;
}

function formatarMoeda(campo, tammax, teclapres, caracter) {

	// Internet Explorer
	if (teclapres == null || teclapres == "undefined") {
		var tecla = -1;
	} else {
		var tecla = teclapres.keyCode;
	}
	if (caracter == null || caracter == "undefined") {
		caracter = ".";
	}
	vr = campo.value;
	if (caracter != "") {
		vr = troca(vr, caracter, "");
	}
	vr = troca(vr, "/", "");
	vr = troca(vr, ",", "");
	vr = troca(vr, ".", "");
	tam = vr.length;
	if (tecla > 0) {
		if (tam < tammax && tecla != 8) {
			tam = vr.length + 1;
		}
		if (tecla == 8) {
			tam = tam - 1;
		}
	}
	if (tecla == -1 || tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96
			&& tecla <= 105) {
		if (tam <= 2) {
			campo.value = vr;
		}
		if ((tam > 2) && (tam <= 5)) {
			campo.value = vr.substr(0, tam - 2) + ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 6) && (tam <= 8)) {
			campo.value = vr.substr(0, tam - 5) + caracter
					+ vr.substr(tam - 5, 3) + ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 9) && (tam <= 11)) {
			campo.value = vr.substr(0, tam - 8) + caracter
					+ vr.substr(tam - 8, 3) + caracter + vr.substr(tam - 5, 3)
					+ ',' + vr.substr(tam - 2, tam);
		}
		if ((tam >= 12) && (tam <= 14)) {
			campo.value = vr.substr(0, tam - 11) + caracter
					+ vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3)
					+ caracter + vr.substr(tam - 5, 3) + ','
					+ vr.substr(tam - 2, tam);
		}
		if ((tam >= 15) && (tam <= 17)) {
			campo.value = vr.substr(0, tam - 14) + caracter
					+ vr.substr(tam - 14, 3) + caracter
					+ vr.substr(tam - 11, 3) + caracter + vr.substr(tam - 8, 3)
					+ caracter + vr.substr(tam - 5, 3) + ','
					+ vr.substr(tam - 2, tam);
		}
	}
}

function maskKeyPress(objEvent) {
	var iKeyCode;
	iKeyCode = objEvent.keyCode;
	if (iKeyCode >= 48 && iKeyCode <= 57)
		return true;
	return false;
}

function autotab(elemento) {
	if (elemento.value.length < elemento.getAttribute("maxlength"))
		return;
	var formulario = elemento.form;
	var els = formulario.elements;
	var x, autotab;
	for ( var i = 0, len = els.length; i < len; i++) {
		x = els[i];
		if (elemento == x && (autotab = els[i + 1])) {
			if (autotab.focus)
				autotab.focus();
		}
	}
}

function imposeMaxLength(Object, MaxLen)
{
  return (Object.value.length <= MaxLen);
}

function valida_horas(edit, ev)

{

	li = new Array(':');

	liE = new Array(58);

	

	somenteNumero(edit,ev,li,liE);

	

	//if(edit.value.length == 2 || edit.value.length == 5)
	if(edit.value.length == 3)

	edit.value += ":";

}

function mascara_data(edit, ev)

{
	//não funcinando para campo richcalendar
	li = new Array('/');

	liE = new Array(58);


	somenteNumero(edit,ev,li,liE);

	if(edit.value.length == 2 || edit.value.length == 5)

	edit.value += "/";

}

function verifica_horas(obj)

{

	if(obj.value.length < 6)

		obj.value = '';

	else

	{

		hr = parseInt(obj.value.substring(0,3));

		mi = parseInt(obj.value.substring(4,6));

		//se = parseInt(obj.value.substring(6,8));

		if((hr < 0) || (mi < 0 || mi > 60))

		{

			obj.value = '';

			//alert('Hora inválida');

		}

	}

}
/*
FUNÇÃO DE FORMATAÇÃO DE CAMPO
PARA CHAMÁ-LA, TEM-SE Q PASSAR 3 PAR?METROS: CAMPO, MÁSCARA, EVENTO
OS PARÂMETROS T?M QUE SER PASSADOS ATRAVÉS DO EVENTO onKeyPress

EX:  <input type="text" name="cep" id="telefone" onKeyPress="return formata(this, '###.###.###-##', event)"> // formata o campo para cep

O CARACTER '#' DEFINE QUE SÓ SERÁ PERMITIDO NÚMEROS
O CARACTER '!' DEFINE QUE É PERMITIDO QUALQUER CARACTER

OBS: COM ESSA FUNÇÃO, NÃO É NECESS?RIO COLOCAR A PROPRIEDADE 'MAXLENGTH' NO CAMPO

*/
function formata(campo, mask, evt) {
 
	if(document.all) { // Internet Explorer
    	key = evt.keyCode; 
    }
    else{ // Nestcape
		key = evt.which;
    }
	if(key == 0 || key == 8 || key == 9){
 		return true;
 	}
 	var stringValorAnterior = campo.value;
 	var string = campo.value;
 	var selStart = 0;
 	var selEnd = 0;
 	if(window.getSelection){        
	    var selLength = campo.textLength;
	    selStart = campo.selectionStart;
	    selEnd = campo.selectionEnd;
	    var s1 = string.substring(0,selStart);
	    var s2 = string.substring(selStart, selEnd);
	    var s3 = string.substring(selEnd, selLength);
		string = s1 + String.fromCharCode(key) + s3;
    }
   	i = string.length;
    var ok = true;
    var charAt = '';
    var maskCharAt = '';    
    var stringResult = '';
    var indice = 0;
    var avaliarProximo = false;
    for(j = 0;j < i;j++){
    	charAt = string.charAt(j);
    	maskCharAt = mask.charAt(indice);
    	avaliarProximo = true;
    	if (maskCharAt != '!'){
    		if(maskCharAt != '#' && maskCharAt != '@'){
	   			stringResult = stringResult + maskCharAt;
	   			if(mask.charAt(indice) != charAt){
	   				indice++;
	   				avaliarProximo = true;
	   			}
	   			else{
		   			avaliarProximo = false;
	   			}
    		}
    		if(avaliarProximo){
   		    	maskCharAt = mask.charAt(indice);
	    		if (maskCharAt == '#') {
			    	var reg = new RegExp("[0-9]");
					stringResult = stringResult + charAt;
					ok = ok && reg.test(charAt);
				} 
				else if (maskCharAt == '@') {
			    	var reg = new RegExp("([a-z]|[A-Z])");
		   			ok = ok && reg.test(charAt);
					stringResult = stringResult + charAt;
					ok = ok && reg.test(charAt);
				}    		
    		}
    	}
    	else{
    		stringResult = stringResult + charAt;
    	}
    	indice++;	
    }
	if(ok){
		campo.value = stringResult;
	}
	else{
		campo.value = stringValorAnterior;
	}
	
	if(window.getSelection && selStart != selEnd){
	   campo.selectionStart = selStart + 1;
       campo.selectionEnd = selStart + 1;
	}
	
	return false;
}
/**
 * Função para formatar campos com letras maiúsculas
 * @param src
 */
function somenteMaiusculo(src){
	var texto = src.value.toUpperCase();
	src.value = texto;
}

/**
 * Função para formatar campos com letras minúsculas
 * @param src
 */
function somenteMinusculo(src){
	var texto = src.value.toLowerCase();
	src.value = texto;	
}

// trim completo
function trim(str) {
	return str.replace(/^\s+|\s+$/g, "");
}

// left trim
function ltrim(str) {
	return str.replace(/^\s+/, "");
}

// right trim
function rtrim(str) {
	return str.replace(/\s+$/, "");
}


function formataFloatBR(campo, casaDecimal) {
	var parteInteira, parteDecimal, result, tam, cont;
	form = document.forms[0];
	result = limpaCampoNumerico(campo.value);
  	tam = result.length;

	if (tam > casaDecimal) {
	  parteInteira = result.substr(0, tam - casaDecimal);
	  parteDecimal = result.substr(tam - casaDecimal);
	  tam = parteInteira.length;
	  cont = tam - (casaDecimal+1);
		while (cont > 0) {
    		parteInteira = parteInteira.substr(0, cont) + "." + parteInteira.substr(cont);
			cont -= (casaDecimal+1);
		}
		result = parteInteira + "," + parteDecimal;
	}
	campo.value = result;
}

/*
 * Retira os simbolos de um campo, retornando apenas os seus digitos
 *
 * PCampo (String) Campo que terá seus caracteres não numéricos excluidos
 */	
function limpaCampoNumerico(Pcampo) {
	var numeros = "0123456789";
	var cont = 0;
	while (cont < Pcampo.length) {
		if (numeros.indexOf(Pcampo.charAt(cont)) < 0)
			Pcampo = Pcampo.substr(0, cont) + Pcampo.substr(cont + 1);
		else
			cont++;
	}
	return Pcampo;
}


//Formata número tipo moeda usando o evento onKeyDown
function formataMoeda(campo, tammax, teclapres, decimal) {
	var tecla = teclapres.keyCode;
	vr = limpar(campo.value, "0123456789");
	tam = vr.length;
	dec = decimal;

	if (tam < tammax && tecla != 8) {
		tam = vr.length + 1;
	}

	if (tecla == 8) {
		tam = tam - 1;
	}

	if (tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) {

		if (tam <= dec) {
			campo.value = vr;
		}

		if ((tam > dec) && (tam <= 5)) {
			campo.value = vr.substr(0, tam - 2) + ","
					+ vr.substr(tam - dec, tam);
		}
		if ((tam >= 6) && (tam <= 8)) {
			campo.value = vr.substr(0, tam - 5) + "." + vr.substr(tam - 5, 3)
					+ "," + vr.substr(tam - dec, tam);
		}
		if ((tam >= 9) && (tam <= 11)) {
			campo.value = vr.substr(0, tam - 8) + "." + vr.substr(tam - 8, 3)
					+ "." + vr.substr(tam - 5, 3) + ","
					+ vr.substr(tam - dec, tam);
		}
		if ((tam >= 12) && (tam <= 14)) {
			campo.value = vr.substr(0, tam - 11) + "." + vr.substr(tam - 11, 3)
					+ "." + vr.substr(tam - 8, 3) + "." + vr.substr(tam - 5, 3)
					+ "," + vr.substr(tam - dec, tam);
		}
		if ((tam >= 15) && (tam <= 17)) {
			campo.value = vr.substr(0, tam - 14) + "." + vr.substr(tam - 14, 3)
					+ "." + vr.substr(tam - 11, 3) + "."
					+ vr.substr(tam - 8, 3) + "." + vr.substr(tam - 5, 3) + ","
					+ vr.substr(tam - 2, tam);
		}
	}

}

function limpar(valor, validos) {
	// retira caracteres invalidos da string
	var result = "";
	var aux;
	for ( var i = 0; i < valor.length; i++) {
		aux = validos.indexOf(valor.substring(i, i + 1));
		if (aux >= 0) {
			result += aux;
		}
	}
	return result;
}

function formataValor(campo,tammax,teclapres) {

    var tecla = teclapres.keyCode;
    var vr = campo.value;
    vr = vr.replace( "/", "" );
    vr = vr.replace( "/", "" );
    vr = vr.replace( ",", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    vr = vr.replace( ".", "" );
    tam = vr.length;

    if (tam < tammax && tecla != 8){ tam = vr.length + 1; }

    if (tecla == 8 ){    tam = tam - 1; }
        
    if ( tecla == 8 || (tecla >= 48 && tecla <= 57) || (tecla >= 96 && tecla <= 105) ){
        if ( tam <= 2 ){ 
             campo.value = vr; }
         tam = tam - 1;
         if ( (tam > 2) && (tam <= 5) ){
             campo.value = vr.substr( 0, tam - 2 ) + ',' + vr.substr( tam - 2, tam ); }
         if ( (tam >= 6) && (tam <= 8) ){
             campo.value = vr.substr( 0, tam - 5 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ); }
         if ( (tam >= 9) && (tam <= 11) ){
             campo.value = vr.substr( 0, tam - 8 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ); }
         if ( (tam >= 12) && (tam <= 14) ){
             campo.value = vr.substr( 0, tam - 11 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam ); }
         if ( (tam >= 15) && (tam <= 17) ){
             campo.value = vr.substr( 0, tam - 14 ) + '.' + vr.substr( tam - 14, 3 ) + '.' + vr.substr( tam - 11, 3 ) + '.' + vr.substr( tam - 8, 3 ) + '.' + vr.substr( tam - 5, 3 ) + ',' + vr.substr( tam - 2, tam );}
    }
    
}

/**
 *  Desabilita Copiar e Colar de input text!
 * @param e
 */
function bloqueiaCopiaCola(e){
	var key;
	if(window.event) { //IE
		key = window.event;
		var ctrl=key.ctrlKey;
		var tecla=key.keyCode;
	} else { //FireFox
		var ctrl=e.ctrlKey;
		var tecla=e.keyCode;
	}

	if ((ctrl && tecla==67) || (ctrl && tecla==86)){
		tecla = 0; 
		if(window.event) // IE
			key.returnValue = false;
		else  // FireFox
			e.preventDefault();
	}
}


