﻿<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url value="resources/css/login.css" var="LoginCss" />
<link href="${LoginCss}" rel="stylesheet" />

<script>

   $("document").ready(function() {
		$("#pdfview").dialog({
			autoOpen : false
		});
	});


	$(function() {

		$("#datepicker").datepicker({
			changeMonth : true,
			changeYear : true
		});

		$("document").ready(function() {
			$("#datepicker").datepicker("option", "dateFormat", 'dd.mm.yy');
			$("#datepicker").datepicker("setDate", "${cert.datacert}");
		});
	});

	
	function submit() {
		var opts = {
				  lines: 11, // The number of lines to draw
				  length: 15, // The length of each line
				  width: 8, // The line thickness
				  radius: 20, // The radius of the inner circle
				  corners: 1, // Corner roundness (0..1)
				  rotate: 0, // The rotation offset
				  direction: 1, // 1: clockwise, -1: counterclockwise
				  color: '#000', // #rgb or #rrggbb or array of colors
				  speed: 1, // Rounds per second
				  trail: 60, // Afterglow percentage
				  shadow: false, // Whether to render a shadow
				  hwaccel: false, // Whether to use hardware acceleration
				  className: 'spinner', // The CSS class to assign to the spinner
				  zIndex: 2e9, // The z-index (defaults to 2000000000)
				  top: '50%', // Top position relative to parent
				  left: '50%' // Left position relative to parent
			   };
	    var spinner = new Spinner(opts).spin(document.getElementById('checker'));
		var errors = 0;
		$("#certview").empty();
		
	    $("#form :input").map(function(){
	         if( !$(this).val() ) {
	              errors++;
	        }   
	    });
	    
	    if(errors > 0){
			$("#certview").html("Все поля должны быть заполнены");
			spinner.stop();
	    } else {
			url = $("#form").attr("action");
			var posting = $.post(url, $("#form").serialize());
             
           	posting.done(function(data) {
			   var content = $(data).find("#message");
			   $("#certview").html(content);
			   spinner.stop();
		    });
	    }
	}

	function viewCertificate(link) {
	    $('#pdf').contents().find('body').attr('style', 'background-color: black'); 
		$("#pdfview").dialog("option", "title", 'Сертификат');
		$("#pdfview").dialog("option", "width", 963);
		$("#pdfview").dialog("option", "height", 570);
		$("#pdfview").dialog("option", "modal", true);
		$("#pdfview").dialog("option", "resizable", false);
		$("#pdfview").dialog({
			buttons : [ 	{ text : "Закрыть",	click : function() {$(this).dialog("close"); $('#pdf').contents().find("body").html('');}} ]
		});

		$("#pdfview").dialog("option", "position", {
			my : "center",
			at : "center",
			of : window
		});
                                   
        $('#pdf').attr('height', $("#pdfview").dialog("option", "height") - 150);
        $('#pdf').attr('width', $("#pdfview").dialog("option", "width") - 40);
        $('#pdf').attr('scrolling', 'yes');
		$('#pdf').attr('src', link);

		$("#pdfview").dialog("open");
	}

	function openCertificate(link) {
		var win=window.open(link,'_blank');
		win.focus();
	}

</script>

<div class="main" id="checker">
	

	<div id="msg" class="ver_message"></div>

        <h3 style="text-align: center; height: 46px;">Проверка сертификата происхождения</h3>

	<form:form id="form" method="POST" commandName="cert" role="form">
		<table class="verification">
			<tr style="height: 46px;">
				<td style="text-align: right; height: 30px; vertical-align: middle;">Номер сертификата&nbsp;</td>
				<td><form:input path="nomercert" class="" placeholder="" id="nomercert" style="height: 28px;" /></td>
			</tr>
			<tr style="height: 46px;">
				<td style="text-align: right; height: 30px; vertical-align: middle;">Номер бланка&nbsp;</td>
				<td><form:input path="nblanka" class="" placeholder="" id="nblanka" style="height: 28px;"/></td>
			<tr style="height: 46px;">
				<td style="text-align: right; height: 30px; vertical-align: middle;">Дата выпуска&nbsp;</td>
				<td><form:input path="datacert" class="" placeholder="" id="datepicker" style="height: 28px;"/></td>
			</tr>
			<tr>
				<td />
				<td>
					<div align="left">
						<a href="javascript:submit()" style="height: 28px; font-size: 120%">Проверить</a>
					</div>
				</td>
			</tr>
		</table>
	</form:form>

	<div id="certview" name="certview" align="center"></div>


	<div id="pdfview" name="pdfview" style="text-align:center;">
	          <iframe class="pdf" id="pdf"></iframe>
                 </div> 
</div>




