<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script>
	$(document).ready(function() {
		$("#fields").removeAttr("multiple");
	});

	function submitreport() {
		url = $("#configreport").attr("action");
		$.post(url, $("#configreport").serialize(), function(result) {
			$("#viewreport").html(result);
		});

	}
</script>

<div id="content">
	<form:form id="configreport" action="makereport.do" method="POST" commandName="reportconfig">

		Сгруппировать сертификаты по:
		<form:select path="fields" items="${reportconfig.headermap}" style="width: 450px;" />
		<a href="javascript:submitreport()"><img src="resources/images/refresh_32.png" alt="Отчет"/></a>
	</form:form>
	
</div>

<fieldset>
<span id="viewreport"></span>
</fieldset>
