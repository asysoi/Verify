<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
		url = $("#form").attr("action");
		var posting = $.post(url, $("#form").serialize());

           	posting.done(function(data) {
			var content = $(data).find("#message");
			$("#certview").empty();
			$("#certview").html(content);
		});
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


</script>

<div class="main">
	

	<div id="msg" class="ver_message"></div>

        <h3 class="page-header" style="text-align: center;">Проверка наличия сертификата</h3>

	<form:form id="form" method="POST" commandName="cert" role="form">
		<table class="verification">
			<tr>
				<td style="text-align: right;">Номер сертификата&nbsp;</td>
				<td><form:input path="nomercert" class="" placeholder="" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">Номер бланка&nbsp;</td>
				<td><form:input path="nblanka" class="" placeholder="" /></td>
			<tr>
				<td style="text-align: right;">Дата выпуска&nbsp;</td>
				<td><form:input path="datacert" class="" placeholder=""
						id="datepicker" /></td>
			</tr>
			<tr>
				<td />
				<td>
					<div align="left">
						<!-- button class="" type="submit">Отправить запрос</button -->
						<a href="javascript:submit()">Проверить</a>
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




