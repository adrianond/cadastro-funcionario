var arrayIdsElementsPage = new Array;
var idundefined = 'idundefined';
var classTypeString = 'java.lang.String';
var classTypeLong = 'java.lang.Long';
var classTypeDate = 'java.util.Date';
var classTypeBoolean = 'java.lang.Boolean';
var classTypeBigDecimal = 'java.math.BigDecimal';



/**
 * Carrega um array global com os ids de todos os componentes da pagina Para ter
 * faciliades em obtencao de valores dos componentes bem como trabalhar com ajax
 */
function carregarIdElementosPagina() {
	 arrayIdsElementsPage = new Array;
	 for (form = 0 ; form <= document.forms.length; form++){
		 var formAtual = document.forms[form];
		 if (formAtual != undefined) {
			 for (i = 0; i< document.forms[form].elements.length; i++){
				 if(document.forms[form].elements[i].id != '') {
					 arrayIdsElementsPage[i] = document.forms[form].elements[i].id;
				 }
			 }	
		 }
	 }
}


/**
 * Retorno o valor do id do componente dentro do documento html passando como
 * parametro a descricao do id declarada em jsf
 * 
 * @param id
 */
function getValorElementPorId(id) {
	 carregarIdElementosPagina();
	 for (i = 0; i< arrayIdsElementsPage.length; i++){
		 var valor =  ""+arrayIdsElementsPage[i];
		 if (valor.indexOf(id) > -1) {
			return valor;
	}
  }	
	 return idundefined;
}


function logout(contextPath) {
	
	var post = 'invalidar_session';
	
	$.ajax({
		type:"POST",
		url: post
	}).always(function(resposta) { 
		document.location = contextPath + '/j_spring_security_logout';
	});
	
}


function invalidarSession(context, pagina) {
	document.location = (context + pagina + ".jsf");
}

function validarSenhaLogin() {

	j_username = document.getElementById("j_username").value;
	j_password = document.getElementById("j_password").value;

	if (j_username === null || j_username.trim() === '') {
		alert("Informe o Login.");
		$('#j_username').focus();
		return false;
	}

	if (j_password === null || j_password.trim() === '') {
		alert("Informe a Senha.");
		$('#j_password').focus();
		return false;
	}

	return true;

}


function abrirMenupop() {
	$("#menupop").show('slow').mouseleave(function() {
		fecharMenupop();
	});
}

function fecharMenupop() {
	if ($("#menupop").is(":visible")){
		$("#menupop").hide("slow");
	}
}

function redirecionarPagina(context, pagina) {
	pagina = pagina + ".jsf";
	document.location = context + pagina;
}


function localeData_pt_br() {
	PrimeFaces.locales['pt'] = {
		closeText : 'Fechar',
		prevText : 'Anterior',
		nextText : 'Próximo',
		currentText : 'Começo',
		monthNames : [ 'Janeiro', 'Fevereiro', 'Marcio', 'Abril', 'Maio',
				'Junho', 'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro',
				'Dezembro' ],
		monthNamesShort : [ 'Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun', 'Jul',
				'Ago', 'Set', 'Out', 'Nov', 'Dez' ],
		dayNames : [ 'Domingo', 'Segunda', 'Terça', 'Quarta', 'Quinta',
				'Sexta', 'Sábado' ],
		dayNamesShort : [ 'Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sab' ],
		dayNamesMin : [ 'D', 'S', 'T', 'Q', 'Q', 'S', 'S' ],
		weekHeader : 'Semana',
		firstDay : 0,
		isRTL : false,
		showMonthAfterYear : false,
		yearSuffix : '',
		timeOnlyTitle : 'São Horas',
		timeText : 'Tempo',
		hourText : 'Hora',
		minuteText : 'Minuto',
		secondText : 'Segundo',
		ampm : false,
		month : 'Mês',
		week : 'Semana',
		day : 'Dia',
		allDayText : 'Todo o Dia'
	};
}

function initTamplete() {
	$(document).ready(function() {
		  $('#menupop').hide();
		  $('#barramenu').hide();
		  $('#barramenu').css("left", "-200px");
		  $('#iniciarmenu').click(function() {
		  	if ($('#barramenu').is(':visible')) {
		  	  ocultarMenu();
		  	} else {
		  	  $('#barramenu').show();
		  	  $('#barramenu').animate({"left":"0px"}, "slow");	
		  	}
		  });
		});
	}


function ocultarMenu() {  
	  $('#barramenu').animate({"left":"-200px"}, "slow", function() {
	  	$('#barramenu').hide();
	  });
	}
	
function validarCampoPesquisa(valor) {
	if ( valor != undefined  &&  valor.value != undefined ) {
		if (valor.value.trim() === '') {
			valor.value = '';
		}else {
			valor.value = valor.value.trim();
		}
	}
}

function redirecionarPage(context, pagina, post) { 
	pagina = pagina + post + ".jsf";
	$.ajax(
			{ type: "POST",
			  url: post
			}).always(function(resposta) { 
					document.location = context + pagina;
			});
}


function addFocoCampo(campo) {
	var id = getValorElementPorId(campo);
	if (id != undefined){
		document.getElementById(getValorElementPorId(id)).focus();
	}
}

/**
 * Gera automaticamente mascara para a tela de pesquisa var classTypeString =
 * 'java.lang.String'; var classTypeLong = 'java.lang.Long'; var classTypeDate =
 * 'java.util.Date'; var classTypeBoolean = 'java.lang.Boolean'; var
 * classTypeBigDecimal = 'java.math.BigDecimal';
 * 
 * @param elemento
 */
function addMascaraPesquisa(elemento) {
	console.log('executou')
	var id = getValorElementPorIdJQuery('valorPesquisa');
	var vals = elemento.split("*");
	var campoBanco = vals[0];
	var typeCampo = vals[1];
	
	$(id).unmask();
	$(id).unbind("keypress"); 
	$(id).unbind("keyup");
	$(id).unbind("focus");
	$(id).val('');
	if (id != idundefined) {
		jQuery(function($) {
			if (typeCampo === classTypeLong) {
				$(id).keypress(permitNumber);
			}
			else if (typeCampo === classTypeBigDecimal) {	
				$(id).maskMoney({precision:4, decimal:",", thousands:"."}); 
			}
			else if (typeCampo === classTypeDate) {
				$(id).mask('99/99/9999');
			}
			else {
				$(id).unmask();
				$(id).unbind("keypress");
				$(id).unbind("keyup");
				$(id).unbind("focus");
				$(id).val('');
			}
			addFocoAoCampo("valorPesquisa");
		});
	}
}

/**
 * Adiciona foco ao campo passado como paramentro
 * 
 * @param campo
 */
function addFocoAoCampo(campo) {
	var id = getValorElementPorId(campo);
	if (id != idundefined) {
		document.getElementById(getValorElementPorId(id)).focus();
	}
}

/**
 * primefaces.js cï¿½digo fonte
 * escapeClientId:function(a){return"#"+a.replace(/:/g,"\\:")}
 * 
 * @param id
 * @returns id
 */
function getValorElementPorIdJQuery(id) {
	var id = getValorElementPorId(id);
	if (id.trim() != idundefined) {
		 return PrimeFaces.escapeClientId(id);
	}
	
	 return idundefined;
}

/**
 * Retorno o valor do id do componente dentro do documento html passando como
 * parametro a descriï¿½ï¿½o do id declarada em jsf
 * 
 * @param id
 */
function getValorElementPorId(id) {
	 carregarIdElementosPagina();
	 for (i = 0; i< arrayIdsElementsPage.length; i++){
		 var valor =  ""+arrayIdsElementsPage[i];
		 if (valor.indexOf(id) > -1) {
			return valor;
	}
  }	
	 return idundefined;
}

function permitirApenasNumero(id) {
	var id = getValorElementPorIdJQuery(id);
	$(id).keypress(permitNumber);
}

function permitNumber(e) {
	var unicode = e.charCode ? e.charCode : e.keyCode;
	if (unicode != 8 && unicode != 9) {
		if (unicode < 48 || unicode > 57) {
			return false;
		}
	}
}

function gerenciaTeclaEnter() {
	
	$(document).ready(function() {
		$('input').keypress(function(e) {
			var code = null;
			code = (e.keyCode ? e.keyCode : e.which);
			return (code === 13) ? false : true;

		});

		$('input[type=text]').keydown(function(e) {
			// Obter o pr󸩭o ?ice do elemento de entrada de texto
			var next_idx = $('input[type=text]').index(this) + 1;

			// Obter o número de elemento de entrada de texto em um documento html
			var tot_idx = $('body').find('input[type=text]').length;

			// Entra na tecla no c󤩧o ASCII
			if (e.keyCode === 13) {
				if (tot_idx === next_idx)
					// Vᠰara o primeiro elemento de texto
					$('input[type=text]:eq(0)').focus();
				else
					// Vᠰara o elemento de entrada de texto seguinte
					$('input[type=text]:eq(' + next_idx + ')').focus();
			}
		});
	});
}

	

function pesquisarUserDestinoPerderFocoDialog(id) {
	console.log('aqui')
	if (id.trim() != '') {
	 $("#usr_destinoMsgDialog").val('');
	}
}

function pesquisarCidadePerderFoco(id) {
	console.log('pesquisarCidadePerderFoco', id)
	if (id.trim() != '') {
		statusDialog.show();
		$("#cid_descricao").val('');
		$.get("findCidade?codCidade=" + id,
			function(resposta) {
			  if (resposta != 'erro' && resposta.trim() != '') {
				var cidadeObj = JSON.parse(resposta);
				$("#cid_codigo").val(cidadeObj.cid_codigo);
				$("#cid_descricao").val(validaDescricao(cidadeObj.cid_descricao));
			}}).always(function() {
			statusDialog.hide();
		});
	}
}


function validaDescricao(descricao) {
	if (descricao === ' ' || descricao.trim() === '') {
		return "Descrição não foi informada.";
	} else {
		return descricao;
	}
}


function copiarValorFantasiaRazao(campo) {
	var idCampoDestino = getValorElementPorIdJQuery('ent_razao');
	var textParaCopia = campo.value;
	
	var textCampoDestino = $(idCampoDestino).val();
	
	if (textCampoDestino === null || textCampoDestino === '' || textCampoDestino === ' '){
		$(idCampoDestino).val(textParaCopia);
	}
}

function addMascaraDecimalMonetaria(id) { 
	var id = getValorElementPorId(id);
	if (id != idundefined) {
		jQuery(function($){
			$("#"+id).maskMoney({precision:2, decimal:",", thousands:"."}); 
		});	
	}
}


function naoPermiteEntradaDeDados(e) {
	return false;
}