<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<spring:url value="resources/css/login.css" var="LoginCss" />
<link href="${LoginCss}" rel="stylesheet" />

<script>
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
</script>

<div class="col-md-10 col-md-offset-2 main">
	<h3 class="page-header">Проверка наличия сертификата</h3>

	<div id="msg" class="ver_message"></div>

	<form:form id="form" method="POST" commandName="cert"
		class="form-signin" role="form">
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
						<a href="javascript:submit()">Отправить запрос</a>
					</div>
				</td>
			</tr>
		</table>
	</form:form>

	<div id="certview" name="certview" align="center"></div>

</div>
